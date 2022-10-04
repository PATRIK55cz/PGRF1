package Model;

public class Line {

    private int x1;
    private int y1;

    private int x2;
    private int y2;

    public boolean dashed;

    public Line(int x1, int y1, int x2, int y2, boolean dashed) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.dashed = dashed;
    }

    public Line(Point start, Point end){
        this.x1 = start.getX();
        this.y1 = start.getY();
        this.x2 = end.getX();
        this.y2 = end.getY();
    }

    public int getStartX() {
        return x1;
    }

    public void setStartX(int x1) {
        this.x1 = x1;
    }

    public int getStartY() {
        return y1;
    }

    public void setStartY(int y1) {
        this.y1 = y1;
    }

    public int getEndX() {
        return x2;
    }

    public void setEndX(int x2) {
        this.x2 = x2;
    }

    public int getEndY() {
        return y2;
    }

    public void setEndY(int y2) {
        this.y2 = y2;
    }
}