package GUI;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 
 * @author kjb00u
 */
public class Selection extends Polygon {

    private Color transparentBlue = new Color(0.72f, 0.72f, 1.0f, 0.6f);
    int size;
    private LinkedList<Point> points;
    private boolean editMode = false;
    //private GraphNode gn;
    //private Rectangle2D rect;

    public Selection(LinkedList<Point> p) {
        int counter = 0;
        size = p.size();
        points = p;
        xpoints = new int[size];
        ypoints = new int[size];
        for (Point ps : p) {
            xpoints[counter] = (int) ps.getX();            
            ypoints[counter] = (int) ps.getY();
            counter++;
        }
        editMode = true;
    }

    public int[] getX() {
        return xpoints;
    }

    public int[] getY() {
        return ypoints;
    }

    public void setX(int[] x) {
        this.xpoints = x;
    }

    public void setY(int[] y) {
        this.ypoints = y;
    }

    public void resetSelection() {
        editMode = false;
    }

    public void drawSelection(Graphics g) {
        if (editMode) {
            g.setColor(transparentBlue);
            g.fillPolygon(xpoints, ypoints, size);
            g.setColor(Color.BLUE);
            g.drawPolygon(xpoints, ypoints, size);
        }
    }

    public int getMinX() {
        if(xpoints.length > 0){
            Arrays.sort(xpoints);
            return xpoints[0];
        } else {
            return 0;
        }
    }

    public int getMinY() {
        Arrays.sort(ypoints);
        return ypoints[0];
    }

    public int getMaxX() {
        Arrays.sort(xpoints);
        return xpoints[xpoints.length - 1];
    }

    public int getMaxY() {
        Arrays.sort(ypoints);
        return ypoints[ypoints.length - 1];
    }
}
