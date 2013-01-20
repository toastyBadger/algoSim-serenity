package GUI;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

/**
 * @author kjb00u
 * @version 0.6
 */
public class GraphNode implements Shape
{
    private LinkedList<Edge> edges = new LinkedList<Edge>();
    private String name;
    private int width, height, x, y,preX,preY;
    private Color colour = Color.BLACK;
    private Color centerColour = Color.WHITE;
    public boolean isNamed = false;

    public int lastSafeX, lastSafeY;

    public GraphNode(String name, int x, int y, int diameter){
        super();
        this.name = name;
        this.width = diameter;
        this.height = diameter;
        this.x = x;
        this.y = y;
        lastSafeX = this.x;
        lastSafeY = this.y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }

    public int getX(){return x;}

    public int getY(){return y;}
    public int getpreX(){return preX;}
    public int getpreY(){return preY;}

    public void setpreX(int x){preX=x;}
    public void setpreY(int y){preY=y;}
    public void setX(int x){
        //setpreX(this.x);
        this.x = x;
    }

    public void setY(int y){
        //setpreY(this.y);
        this.y = y;
    }

    public int getCentreX(){
            return (x + (width/2));
    }

    public int getCentreY(){
    	return (y + (height/2));
    }

    public int centerX(Rectangle2D r){
        return (int) r.getCenterX();
    }

    public int centerY(Rectangle2D r){
        return (int) r.getCenterY();
    }

    public Point nodeEdge(Rectangle2D r){
        int rx, ry;
        rx = (int) (r.getMaxX() - r.getMinX())/2;
        ry = (int) (r.getMaxY() - r.getMinY())/2;

        return new Point(rx, ry);
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    public void dereferenceEdge(Edge edge){
        if(this.edges.contains(edge)){
            this.edges.remove(edge);
        }
    }

    public LinkedList<Edge> getEdges(){
        return this.edges;
    }

    public void drawNode(Graphics g){
        FontMetrics metrics = g.getFontMetrics();
        //g.setFont(g.getFont().deriveFont(Font.BOLD, 16.0f));

        g.setColor(this.colour);
        g.fillOval(x, y, width, height);

        g.setColor(centerColour);
        g.fillOval(x + 2, y + 2, width - 4, height - 4);

        g.setColor(this.colour);
        g.drawString(name, x + (width >> 1) - (metrics.stringWidth(name) >> 1), y + (height >> 1));

        // this draws the bounding box for the
        //g.drawRect(x, y, width, height);
    }

    public void setasStart(){
        centerColour = Color.GREEN;
    }

    public void setasEnd(){
        centerColour = Color.red;
    }

    public void resetNode(){
        centerColour = Color.white;
    }

    public boolean contains(Point2D p){
        return getBounds2D().contains(p);
    }

    public boolean contains(Rectangle2D r){
        return getBounds2D().contains(r);
    }

    public void setColour(Color colour){
        this.colour = colour;
    }

    public boolean contains(double x, double y) {return false;}

    public boolean contains(double x, double y, double w, double h) {return false;}

    public Rectangle getBounds() {return null;}

    public Rectangle2D getBounds2D(){
        Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
        return rect;
    }

    public PathIterator getPathIterator(AffineTransform at) {return null;}

    public PathIterator getPathIterator(AffineTransform at, double flatness) {return null;}

    public boolean intersects(Rectangle2D r){
        return contains(r);
    }

    public boolean intersects(double x, double y, double w, double h) {return false;}

    public String toString(){
        return getName()+", Size "+width+" at Location: "+x+"*"+y;
    }
}


