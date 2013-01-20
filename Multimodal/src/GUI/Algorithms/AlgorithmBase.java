package GUI.Algorithms;


import GUI.Edge;
import GUI.GUI;
import GUI.Graph;
import GUI.GraphNode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is the base class for all of the possible algorithms.
 * @version 1.1
 * #####
 * This version has been updated to fit with the new structure
 *
 * @version 1
 * #####
 * Initial build
 *
 * @author tab00u
 */
public class AlgorithmBase extends JPanel implements Serializable {

    private static long serialVersionUID = 19743432l;

    private GraphNode rootNode;
    private GraphNode endNode;
    private Graphics graphic;
    private GUI gui = null;
    private int pauseDUR = 0;
    private LinkedList<GraphNode> nodes;
    private LinkedList<Edge> edges;
    private Graph graph;
    private TextArea STDout;
    private TextArea PQout;
    private nodeList current = null;
    private static boolean directed = false;
    private Color active = Color.YELLOW;
    private Color visited = Color.RED;
    private Color used = Color.BLUE;
    private Color start_n = used;
    private Color goal_n = used;
    private int endID = -1;
    private String guess;
    private String nextNode;

    private GraphStage stage = null;
    private ProcessedGraph process = new ProcessedGraph();

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the directed
     */
    public static boolean isDirected() {
        return directed;
    }

    /**
     * @param aDirected the directed to set
     */
    public static void setDirected(boolean aDirected) {
        directed = aDirected;
    }

    public void setD(boolean b) {
        setDirected(b);
    }

    public AlgorithmBase(Graph graph, Graphics grp, TextArea out, TextArea pqout, GUI gui) {
        this.graph = graph;
        this.gui = gui;
        STDout = out;
        STDout.setText("");
        PQout = pqout;
        PQout.setText("");
        graphic = grp;
        nodes = graph.getNodes();
        edges = graph.getEdges();

        STDout.setVisible(true);
        PQout.setVisible(true);
    }

    /**
     * This Method will run the algorithm and cause the
     */
    public void run() {
        getSTDout().setText("Method not yet Implemented");
    }

    /**
     * This traces and displays the route taken for a given minimum spanning
     * tree (Or at least on formatted using nodeList[])
     * @deprecated
     * @param n - a list of nodes that have been expanded, so each node has a
     * predecessor.
     */
    protected void trace(nodeList[] n) {
        // this loop allows the algorithm to show the path taken on the canvas
        for (int i = 0; i < n.length; i++) {
            nodeList currentNode = n[i];
            if (n[i] != null) {
                currentNode.node.setColour(getUsed());
                if (currentNode.precedentID != -1) {
                    for (int j = n.length - 1; j >= 0; j--) {
                        if (n[j] != null) {
                            nodeList pred = n[j];
                            if (pred.ID == currentNode.precedentID) {
                                // ok now we should have the end node we need to find the edge between them
                                Iterator<Edge> eIter = currentNode.node.getEdges().iterator();
                                while (eIter.hasNext()) {
                                    Edge e = eIter.next();
                                    GraphNode end = e.getAdjoiningNode(currentNode.node);
                                    if (end.equals(pred.node)) {
                                        // the above should find an edge from i (current) to j (pred)
                                        e.setColour(getUsed());
                                        update(getGraphic());
                                        //pause();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //rootNode.setColour(start_n);
        update(getGraphic());
    }

    /**
     * @deprecated
     * @param n
     */
    protected void traceD(nodeList[] n) {
        // this loop allows the algorithm to show the path taken on the canvas
        int i = getEndID();
        // at this point i is the address of the end node in the node list
        boolean notComplete = true;
        while (notComplete) {
            LinkedList<Edge> es = n[i].node.getEdges();
            for (Edge e : es) {
                if (e.getAdjoiningNode(n[i].node) == n[n[i].precedentID].node) {
                    if (isDirected()) {
                        e.setDColour(getUsed());
                    } else {
                        e.setColour(getUsed());
                    }
                }
            }
            i = n[i].precedentID;
            if (n[i].node == getRootNode()) {
                notComplete = false;
            } else {
                n[i].node.setColour(getUsed());
            }
        }
        /*
         * for (int i = 0; i < n.length; i++) { nodeList current = n[i]; if
         * (n[i] != null) { current.node.setColour(used); if
         * (current.precedentID != -1) { for (int j = n.length - 1; j >= 0; j--)
         * { if (n[j] != null) { nodeList pred = n[j]; if (pred.ID ==
         * current.precedentID) { // ok now we should have the end node we need
         * to find the edge between them Iterator<Edge> eIter =
         * current.node.getEdges().iterator(); while (eIter.hasNext()) { Edge e
         * = eIter.next(); GraphNode end = e.getAdjoiningNode(current.node); if
         * (end.equals(pred.node)) { // the above should find an edge from i
         * (current) to j (pred) e.setDColour(used); update(graphic); //pause();
         * break; } } } } } } } }
         */
        getRootNode().setColour(getStart_n());
        getEndNode().setColour(getGoal_n());
        update(getGraphic());
    }

    /**
     * @deprecated
     */
    protected void pause() {
        pause(getPauseDUR(), false);
    }

    /**
     *@deprecated
     * @param dur
     * @param bool
     */
    protected void pause(int dur, boolean bool) {
        if (getGraph().teachingMode && bool) {
            setGuess(JOptionPane.showInputDialog("Which Node do you think will be expanded next?"));
            if (getGuess().equalsIgnoreCase(getNextNode())) { // ignore case here as we are checking for correct grammar
                JOptionPane.showMessageDialog(null, "Correct! The next node to be expanded is " + getGuess());
            } else {
                JOptionPane.showMessageDialog(null, "Incorrec! The next node to be expanded is actually " + getNextNode());
            }
        } else {
            try {
                Thread.sleep(dur);
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    public void update(Graphics ink) {
        if (!isDirected()) {
            for (Edge e : getEdges()) {
                e.draw_Edge(ink);
            }
        } else {
            for (Edge e : getEdges()) {
                e.draw_DEdge(ink);
            }
        }
        for (GraphNode gn : getNodes()) {
            gn.drawNode(ink);
        }
    }

    protected void printSTD(String str) {
        getSTDout().setText(getSTDout().getText().concat(str));
    }

    protected void printPQ(String str) {
        int i = getPQout().getRows() - str.length();
        for (; i > 0; i--) {
            str = str + " ";
        }
        getPQout().setText(getPQout().getText().concat(str));
    }

    protected void clearSTD() {
        getSTDout().setText("");
    }

    protected void clearPQ() {
        getPQout().setText("PQ: \n");
    }

    /**
     * @return the rootNode
     */
    public GraphNode getRootNode() {
        return rootNode;
    }

    /**
     * @param rootNode the rootNode to set
     */
    public void setRootNode(GraphNode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * @return the endNode
     */
    public GraphNode getEndNode() {
        return endNode;
    }

    /**
     * @param endNode the endNode to set
     */
    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
    }

    /**
     * @return the graphic
     */
    public Graphics getGraphic() {
        return graphic;
    }

    /**
     * @param graphic the graphic to set
     */
    public void setGraphic(Graphics graphic) {
        this.graphic = graphic;
    }

    /**
     * @return the gui
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * @param gui the gui to set
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * @return the pauseDUR
     */
    public int getPauseDUR() {
        return pauseDUR;
    }

    /**
     * @param pauseDUR the pauseDUR to set
     */
    public void setPauseDUR(int pauseDUR) {
        this.pauseDUR = pauseDUR;
    }

    /**
     * @return the nodes
     */
    public LinkedList<GraphNode> getNodes() {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(LinkedList<GraphNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * @return the edges
     */
    public LinkedList<Edge> getEdges() {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(LinkedList<Edge> edges) {
        this.edges = edges;
    }

    /**
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @param graph the graph to set
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * @return the STDout
     */
    public TextArea getSTDout() {
        return STDout;
    }

    /**
     * @param STDout the STDout to set
     */
    public void setSTDout(TextArea STDout) {
        this.STDout = STDout;
    }

    /**
     * @return the PQout
     */
    public TextArea getPQout() {
        return PQout;
    }

    /**
     * @param PQout the PQout to set
     */
    public void setPQout(TextArea PQout) {
        this.PQout = PQout;
    }

    /**
     * @return the current
     */
    public nodeList getCurrent() {
        return current;
    }

    /**
     * @param current the current to set
     */
    public void setCurrent(nodeList current) {
        this.current = current;
    }

    /**
     * @return the active
     */
    public Color getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Color active) {
        this.active = active;
    }

    /**
     * @return the visited
     */
    public Color getVisited() {
        return visited;
    }

    /**
     * @param visited the visited to set
     */
    public void setVisited(Color visited) {
        this.visited = visited;
    }

    /**
     * @return the used
     */
    public Color getUsed() {
        return used;
    }

    /**
     * @param used the used to set
     */
    public void setUsed(Color used) {
        this.used = used;
    }

    /**
     * @return the start_n
     */
    public Color getStart_n() {
        return start_n;
    }

    /**
     * @param start_n the start_n to set
     */
    public void setStart_n(Color start_n) {
        this.start_n = start_n;
    }

    /**
     * @return the goal_n
     */
    public Color getGoal_n() {
        return goal_n;
    }

    /**
     * @param goal_n the goal_n to set
     */
    public void setGoal_n(Color goal_n) {
        this.goal_n = goal_n;
    }

    /**
     * @return the endID
     */
    public int getEndID() {
        return endID;
    }

    /**
     * @param endID the endID to set
     */
    public void setEndID(int endID) {
        this.endID = endID;
    }

    /**
     * @return the guess
     */
    public String getGuess() {
        return guess;
    }

    /**
     * @param guess the guess to set
     */
    public void setGuess(String guess) {
        this.guess = guess;
    }

    /**
     * @return the nextNode
     */
    public String getNextNode() {
        return nextNode;
    }

    /**
     * @param nextNode the nextNode to set
     */
    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }
}
