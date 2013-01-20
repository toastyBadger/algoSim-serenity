package GUI;

import java.awt.*;

/**
 * Holds information about edges in a graph
 * @author kjb00u
 */
public class Edge{

    public GraphNode[] node = new GraphNode[2];
    private double weight;
    private int ex1, ex2, ey1, ey2;
    private Color colour = Color.BLACK;
    private Color Dcolour=Color.BLACK;
    private Point value;
    
    /**
     * 
     * @param value -- the weight of the edge
     * @param x1 -- used for drawing, the x value of where it should be drawn from
     * @param y1 -- 
     * @param x2
     * @param y2
     * @param n1 -- the first node -- effectively the start node
     * @param n2 -- the second node
     */
    public Edge(Double value, GraphNode n1, GraphNode n2){
        super();
        this.weight = value;
        this.ex1 = n1.getCentreX();
        this.ey1 = n1.getCentreY();
        this.ex2 = n2.getCentreX();
        this.ey2 = n2.getCentreY();
        node[0] = n1;
        node[1] = n2;

        // add these edges to the nodes
        n1.addEdge(this);
        n2.addEdge(this);
    }
   
    
    public Point getStart(){return (new Point(this.ex1, this.ex2));}

    public Point getEnd(){return (new Point(this.ey1, this.ey2));}

    public void updateStart(Point p){
        this.ex1 = p.x;
        this.ey2 = p.y;
    }

    public void updateEnd(Point p){
        this.ex2 = p.x;
        this.ey2 = p.y;
    }

    public GraphNode getAdjoiningNode(GraphNode currentNode){
        if(node[0] != null && node[1] != null){
                return (node[0].equals(currentNode)) ? node[1] : node[0];
        }else{
                return null;
        }
    }

    public GraphNode[] getNodes(){
        return node;
    }

    /**
     * Simple command to return the weighting for the
     * @return
     */
    public double getWeight(){
        return weight;
    }

    public void resetWeight(Double d, Graphics g){
        this.weight = d;
        drawEdge(g);
    }

    public Point getValue(){
        return this.value;
    }

    public void draw_Edge(Graphics g){
        FontMetrics metrics = g.getFontMetrics();
        int centerX1 = node[0].centerX(node[0].getBounds2D());
        int centerY1 = node[0].centerY(node[0].getBounds2D());
        int centerX2 = node[1].centerX(node[1].getBounds2D());
        int centerY2 = node[1].centerY(node[1].getBounds2D());

    	value = new Point(centerX1 + ((centerX2 - centerX1) >> 1) - (metrics.stringWidth(String.valueOf(weight)) >> 1), centerY1 + ((centerY2 - centerY1) >> 1));
    	//g.drawString(String.valueOf(weight), node[0].getX() + ((node[1].getX() - node[0].getX()) >> 1) - (metrics.stringWidth(String.valueOf(weight)) >> 1), node[0].getY() + ((node[1].getY() - node[0].getY()) >> 1));

    	drawEdge(g);
    }

    private void drawEdge(Graphics g){
        FontMetrics metrics = g.getFontMetrics();
        //g.setFont(g.getFont().deriveFont(Font.BOLD, 12.0f));

        g.setColor(colour);
        int strX = node[0].getCentreX(), strY = node[0].getCentreY(), endX = node[1].getCentreX(), endY = node[1].getCentreY();
        g.drawLine(strX, strY, endX, endY);
        g.setColor(Color.black);
        if(weight != 0.0){
            g.drawString(String.valueOf(weight), strX + ((endX - strX) >> 1) - (metrics.stringWidth(String.valueOf(weight) + 30000) >> 1), strY + ((endY - strY) >> 1) + 20);
        }
    }

    public void draw_DEdge(Graphics g)
    {
    	FontMetrics metrics = g.getFontMetrics();
        int centerX1 = node[0].centerX(node[0].getBounds2D());
        int centerY1 = node[0].centerY(node[0].getBounds2D());
        int centerX2 = node[1].centerX(node[1].getBounds2D());
        int centerY2 = node[1].centerY(node[1].getBounds2D());

    	value = new Point(centerX1 + ((centerX2 - centerX1) >> 1) - (metrics.stringWidth(String.valueOf(weight)) >> 1), centerY1 + ((centerY2 - centerY1) >> 1));

    	drawDEdge(g);
    }

    private void drawDEdge(Graphics g){
        FontMetrics metrics = g.getFontMetrics();
        //g.setFont(g.getFont().deriveFont(Font.BOLD, 12.0f));
        int centerX1 = node[0].centerX(node[0].getBounds2D());
        int centerY1 = node[0].centerY(node[0].getBounds2D());
        int centerX2 = node[1].centerX(node[1].getBounds2D());
        int centerY2 = node[1].centerY(node[1].getBounds2D());

        g.setColor(this.Dcolour);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        //g2.drawLine(centerX1, centerY1, centerX2, centerY2);

        //g2.drawLine(centerX1, centerY1, centerX2, centerY2);
        //g2.fill(createArrowShape());

        float arrowWidth = 10.0f ;
        float theta = 0.423f ;
        int[] xPoints = new int[ 3 ] ;
        int[] yPoints = new int[ 3 ] ;
        float[] vecLine = new float[ 2 ] ;
        float[] vecLeft = new float[ 2 ] ;
        float fLength;
        float th;
        float ta;
        float baseX, baseY ;

        // change side of arrow head if something > or < something
        // if any of the 2 nodes are in -x
        if((node[1].getX() > node[0].getX() && node[1].getY() > (node[0].getY() >> 1)) && (node[1].getY() < (node[0].getY() + (node[0].getY() >> 1))))
        {
                xPoints[ 0 ] = (centerX2 - node[1].nodeEdge(node[1].getBounds2D()).x);
                yPoints[ 0 ] = ((centerY2 - node[1].nodeEdge(node[1].getBounds2D()).y)) + node[1].nodeEdge(node[1].getBounds2D()).y;
        }
        // if any of the 2 nodes are in +x
        else if((node[1].getX() < node[0].getX() && node[1].getY() > (node[0].getY() >> 1)) && (node[1].getY() < (node[0].getY() + (node[0].getY() >> 1))))
        {
                xPoints[ 0 ] = (centerX2 - node[1].nodeEdge(node[1].getBounds2D()).x) + (node[1].nodeEdge(node[1].getBounds2D()).x << 1);
                yPoints[ 0 ] = ((centerY2 - node[1].nodeEdge(node[1].getBounds2D()).y)) + node[1].nodeEdge(node[1].getBounds2D()).y;
        }
        // if any of the 2 nodes are in -y
        else if(((node[0].getY() < node[1].getY() && node[0].getX() < node[1].getX()) || ((node[0].getY() < node[1].getY() && node[0].getX() > node[1].getX()))
                    || (node[1].getY() < node[0].getY() && node[1].getX() < node[0].getX()) && (node[1].getY() < node[0].getY() && node[1].getX() > node[0].getX()))
                    || (node[0].getX() == node[1].getX() && node[0].getY() < node[1].getY()) || (node[0].getX() == node[1].getX() && node[1].getY() < node[0].getY()))
        {
            xPoints[ 0 ] = (centerX2 - node[1].nodeEdge(node[1].getBounds2D()).x) + (node[1].nodeEdge(node[1].getBounds2D()).x);
                yPoints[ 0 ] = (centerY2 - node[1].nodeEdge(node[1].getBounds2D()).y);
        }
        // if any of the 2 nodes are in +y
        else if(((node[0].getY() > node[1].getY() && node[0].getX() < node[1].getX()) || (node[0].getY() > node[1].getY() && node[0].getX() > node[1].getX()))
                    || (node[1].getY() > node[0].getY() && node[1].getX() < node[0].getX()) || (node[1].getY() > node[0].getY() && node[1].getX() > node[0].getX())
                    || (node[0].getX() == node[1].getX() && node[0].getY() < node[1].getY()) || (node[0].getX() == node[1].getX() && node[1].getY() < node[0].getY()))
        {
            xPoints[ 0 ] = (centerX2 - node[1].nodeEdge(node[1].getBounds2D()).x) + (node[1].nodeEdge(node[1].getBounds2D()).x);
                yPoints[ 0 ] = (centerY2 - node[1].nodeEdge(node[1].getBounds2D()).y) + (node[1].nodeEdge(node[1].getBounds2D()).y << 1);
        }


        // build the line vector
        vecLine[ 0 ] = (float)xPoints[ 0 ] - centerX1 ;
        vecLine[ 1 ] = (float)yPoints[ 0 ] - centerY1 ;

        // build the arrow base vector - normal to the line
        vecLeft[ 0 ] = -vecLine[ 1 ] ;
        vecLeft[ 1 ] = vecLine[ 0 ] ;

        // setup length parameters
        fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
        th = arrowWidth / ( 2.0f * fLength ) ;
        ta = arrowWidth / ( 2.0f * ( (float)Math.tan( theta ) / 2.0f ) * fLength ) ;

        // find the base of the arrow
        baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
        baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

        // build the points on the sides of the arrow
        xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
        yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
        xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
        yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );

        g.setColor(this.Dcolour);
        g.drawLine(centerX1, centerY1, (int)baseX, (int)baseY);
        //g.drawLine(centerX1, centerY1, centerX2, centerY2);
        g.fillPolygon(xPoints, yPoints, 3) ;        
        g.setColor(this.Dcolour);
        if(weight > 0){
            g.drawString(String.valueOf(weight), centerX1 + ((centerX2 - centerX1) >> 1) - (metrics.stringWidth(String.valueOf(weight)) >> 1), centerY1 + ((centerY2 - centerY1) >> 1));
        }
    }
    public void setDColour(Color colour){
    	this.Dcolour=colour;
    	
    }
    public void setColour(Color colour)
    {
            this.colour = colour;
    }
}


