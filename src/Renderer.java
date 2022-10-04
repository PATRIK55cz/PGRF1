import view.Raster;

import java.awt.image.BufferedImage;

public class Renderer {
    private BufferedImage img;
    private int color;

    public Renderer(BufferedImage img, int color) {
        this.img = img;
        this.color = color;
    }

    public void Draw(int x1, int y1, int x2, int y2) {

        float k = (float) (y2 - y1) / (x2 - x1); // tangens (úhel směrnice)
        float q = (y1 - k * x1);

        if (x1 == x2) {
            if (y2 > y1) {      // dolní vertikální úsečka
                for (int y = y1; y <= y2; y++) {
                    img.setRGB(x1, y, color);
                }
            } else {            // horní vertikální úsečka
                for (int y = y2; y <= y1; y++) {
                    img.setRGB(x1, y, color);
                }
            }
        } else {
            if (k <= 1 && k >= -1) {
                if (x1 > x2) { // prohození pořadí proměných, aby x1 bylo menší
                    int temp = x1;
                    x1 = x2;
                    x2 = temp;

                }
                for (int x = x1; x <= x2; x++) {
                    int y = Math.round(k * x + q);
                    img.setRGB(x, y, color);
                }
            } else {
                if (y1 > y2) { // prohození pořadí proměných, aby y1 bylo menší

                    int temp = y1;
                    y1 = y2;
                    y2 = temp;
                }
                for (int y = y1; y <= y2; y++) {
                    int x = Math.round((y - q) / k);
                    img.setRGB(x, y, color);
                }
            }
        }
    }

}