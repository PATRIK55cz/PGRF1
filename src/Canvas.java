import Model.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
public class Canvas {
	private static int CLEAR_COLOR = 0xff2f2f2f;
	int startX;
	int startY;
	int endX = 0;
	int endY = 0;
	int linei = 0;
	String choice = "Line";
	JFrame frame;
	JPanel panel;
	BufferedImage img;
	boolean dashedline = false;
	boolean triangle;
	private List<Line> lines = new ArrayList<>();
	private List<Point> polygon = new ArrayList<>();
	KeyListener keyListener;
	MouseAdapter mouse;
	Renderer renderer;

	public Canvas(){
		frame = new JFrame();
		frame.setTitle("PRGF");
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		img = new BufferedImage(800,600, BufferedImage.TYPE_INT_ARGB);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(800, 600));
		renderer = new Renderer(img,0xffff0000);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		initListeners();

		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mouse);
		frame.addKeyListener(keyListener);
	}

	public void initListeners(){
		this.keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_K){
					choice = "Polygon";
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_C: clear(CLEAR_COLOR, true); present(); break;
					case KeyEvent.VK_K: if(choice == "Line")
						choice = "Polygon";
					else choice = "Line"; break;
					case KeyEvent.VK_T: triangle = !triangle; break;
					default: break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		};

		this.mouse = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				startX = e.getX();
				startY = e.getY();
				switch(choice){
					case "Polygon": polygon.add(new Point(startX,startY)); break;
					case "Line": lines.add(new Line(startX, startY,endX,endY, dashedline)); break;
					default: System.out.println("HOW"); break;
				}
				present();
			}

			public void mouseReleased(MouseEvent e) {
				clear(CLEAR_COLOR, false);
				endX = e.getX();
				endY = e.getY();
				switch(choice){
					case "Polygon": polygon.add(new Point(endX,endY)); break;
					case "Line": lines.get(linei).setEndX(endX);
						lines.get(linei).setEndY(endY);
						if(lines.get(linei).dashed) drawDashedLine(img.getGraphics(), startX,startY,endX,endY);
						else renderer.Draw(startX, startY, endX, endY);
						linei++; break;
				}
				redraw();
				present();
			}

			public void mouseDragged(MouseEvent e) {
				clear(CLEAR_COLOR, false);
				endX = e.getX();
				endY = e.getY();
				switch(choice){
					case "Polygon": renderer.Draw((int) polygon.get(polygon.size()-1).getX(), (int) polygon.get(polygon.size()-1).getY(),endX,endY);
						if(polygon.size()-1 > 1) renderer.Draw((int) polygon.get(0).getX(),(int) polygon.get(0).getY(),endX,endY);
						break;
					case "Line":
						if(lines.get(linei).dashed) drawDashedLine(img.getGraphics(), startX,startY,endX,endY);
						else renderer.Draw(startX, startY, endX, endY); break;
				}
				redraw();
				present();
			}
		};
	}


	public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {

		// Create a copy of the Graphics instance
		Graphics2D g2d = (Graphics2D) g.create();

		// Set the stroke of the copy, not the original
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
				0, new float[]{9}, 0);
		g2d.setStroke(dashed);

		// Draw to the copy
		g2d.drawLine(x1, y1, x2, y2);

		// Get rid of the copy
		g2d.dispose();
	}
	public void clear(int color, boolean all) {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(color));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
		if(all) {
			lines = new ArrayList<>();
			polygon = new ArrayList<>();
			linei = 0;
		}
	}
	public void present() {
		if (panel.getGraphics() != null)
			panel.getGraphics().drawImage(img, 0, 0, null);
	}

	public void start() {
		clear(CLEAR_COLOR, false);
		present();
	}

	public void redraw(){
		for(int i = 0;i< lines.size() - 1;i++)
		{
			if(lines.get(i).dashed)
				drawDashedLine(img.getGraphics(), lines.get(i).getStartX(),lines.get(i).getStartY(),lines.get(i).getEndX(),lines.get(i).getEndY());
			else
				renderer.Draw(lines.get(i).getStartX(), lines.get(i).getStartY(), lines.get(i).getEndX(), lines.get(i).getEndY());
		}
		for(int i = 0; i < polygon.size()-1;i++)
		{
			renderer.Draw((int) polygon.get(i).getX(), (int) polygon.get(i).getY(), (int) polygon.get(i+1).getX(), (int) polygon.get(i+1).getY());
			if(polygon.size() > 1) renderer.Draw((int) polygon.get(0).getX(), (int) polygon.get(0).getY(), (int) polygon.get(polygon.size() -1).getX(), (int) polygon.get(polygon.size() -1).getY());
		}
	}
}