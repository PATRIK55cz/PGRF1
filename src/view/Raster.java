package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.TimerTask;

public class Raster extends JPanel {

    private final BufferedImage img; // objekt pro zápis pixelů
    private final Graphics g; // objekt nad kterým jsou k dispozici grafické funkce

    private static final int FPS = 1000 / 30;


    Raster() {
        setPreferredSize(new Dimension(800, 600));
        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        g = img.getGraphics();
        clear();
        //setLoop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

    /*private void setLoop() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, FPS);
    }*/

    public void drawPixel(int x, int y, int color) {
        img.setRGB(x, y, color);
    }

    public void clear() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);
    }
}