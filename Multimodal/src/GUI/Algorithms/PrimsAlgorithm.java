package GUI.Algorithms;


import GUI.Edge;
import GUI.GUI;
import GUI.Graph;
import GUI.GraphNode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 * Algorithm: Prim's -- Takes the list of Edges and GraphNodes, returns a
 * minimum spanning tree for this set...
 *
 * @author tab00u
 * @version 0.9.8
 */
public class PrimsAlgorithm extends AlgorithmBase {

    private static final long serialVersionUID = 2746336628312937l;
    private nodeList[] nl = null;
    private LinkedList<nodeList> pq = new LinkedList<>();

    public PrimsAlgorithm(Graph graph, Graphics grp, TextArea out, TextArea pqout, GUI gui) {
        super(graph, grp, out, pqout, gui);
    }

    /**
     * Finds the ID of the Node passed to it
     *
     * @param node The node whose ID you wish to find from the nodeList
     * @return the ID of the node Passed to it
     */
    protected final int findNodeID(GraphNode node) {
        for (int i = 0; i < getNl().length; i++) {
            if (getNl()[i].node.equals(node)) { // compares node hashcodes
                return getNl()[i].ID;
            }
        }
        return -1; // -1 will never exist... and will actually break the program...
    }

    @Override
    /**
     * This runs the algorithm if all required parts are present (nodes and or
     */
    public final void run() {
        getGui().algoRunning();
        if (!getNodes().isEmpty()) {
            run(getNodes());
        } else {
            JOptionPane.showMessageDialog(this, "No Node Graph Found", "ERROR: Algorithm Error", JOptionPane.ERROR_MESSAGE);
            getGui().algoFinished();
        }
    }

    private void run(LinkedList<GraphNode> g) {
        int start = (int) Math.round(Math.random() * (g.size() - 1)); // should randomly chose a number between 0 and the number of nodes in the graph;
        pause();

        int n = g.size(); // gets the size of the list
        setNl(new nodeList[n]); // makes the list the same size as the list
        for (int i = 0; i < n; i++) { // steps through each node and assigns it to the nodeList
            getNl()[i] = new nodeList(getNodes().get(i), i); // also assings each node an id.
        }
        nl[start].distance = 0;

        //initialise linked List for the Priority Queue
        setPq(new LinkedList<nodeList>());
        setRootNode(getNl()[start].node);
        getPq().add(getNl()[start]);
        printSTD("Starting");
        pause(100, false);
        for (int i = 0; i < 10; i++) {
            printSTD(".");
            pause(getPauseDUR() / 10, false);
        }
        printSTD("\n -- Root Node is: " + getRootNode().getName() + "\n");

        while (!pq.isEmpty()) {
            nodeList current = getPq().removeFirst();

            // uncommenting these lines will make the program print out the PQ at each step
            // as this is something that Ender wants us to do at somepoint, i've made sure it works now...
            clearPQ();
            Iterator<nodeList> pqList = getPq().listIterator();
            if (pqList.hasNext()) {
                while (pqList.hasNext()) {
                    nodeList nn = pqList.next();
                    printPQ("<" + nn.node.getName() + " : " + nn.distance + "> ");
                }
            } else {
                printPQ("<empty>");
            }

            printSTD("-- Expanding Node " + current.node.getName() + ":\n");
            if (!nl[current.ID].visited) { // checks the node that has been taken out of the pq
                current.node.setColour(getActive());
                update(getGraphic());
                pause();
                Iterator<Edge> edgeIter = getNl()[current.ID].node.getEdges().iterator(); // make an iterator out of the edges assigned to the node.
                if (edgeIter.hasNext()) {
                    while (edgeIter.hasNext()) {
                        Edge e = edgeIter.next();
                        int nID = findNodeID(e.getAdjoiningNode(current.node));
                        printSTD("\t -- Edge Found to Node " + getNl()[nID].node.getName() + " weight " + e.getWeight());
                        double eWeight = e.getWeight();
                        nl[current.ID].visited = true;
                        if (!nl[nID].visited) {
                            if (getNl()[nID].distance > eWeight) {
                                // if the weight of the new edge is less than the distance already on file,
                                // then we need to swap that one out for a new one

                                if (getPq().contains(getNl()[nID])) { // if this returns true then whats in there will be at a high weighting than the new one, so remove it!
                                    // check for the old edge? if this one is already in the pq then it has a pred, look for that and then find the edge between them
                                    nodeList old = getNl()[getNl()[nID].precedentID]; // the old precedent of this new node
                                    LinkedList<Edge> es = old.node.getEdges(); // the edges for the old pred node
                                    LinkedList<Edge> es2 = getNl()[nID].node.getEdges(); // the edges for the new node
                                    for (Edge oldE : es) {
                                        for (Edge oldE2 : es2) {
                                            if (oldE2.equals(oldE)) {
                                                oldE2.setColour(Color.DARK_GRAY); // if the edge of the old pred is the same edge as the one from the new edge then set it to be black
                                                update(getGraphic());
                                                //pause();
                                                break;
                                            }
                                        }
                                    }
                                    getPq().remove(getNl()[nID]);
                                    printSTD("\t ## Re-added to PQ (Old Weight: " + getNl()[nID].distance + ") ##\n");
                                } else {
                                    printSTD("\t ## Added to PQ ##\n");
                                }

                                nodeList newNode = getNl()[nID];
                                newNode.distance = eWeight;
                                newNode.precedentID = current.ID; // set this to be the id of the node from which we came :D

                                // this next segment sorts the PQ and makes sure it is in order
                                if (getPq().size() != 0) { // check to make sure that there is actually something in the pq
                                    for (int i = getPq().size() - 1; i >= 0; i--) { // if there is...
                                        if (getPq().get(i).distance <= newNode.distance) {
                                            // if the current node in the list has a weight lower than, or equal to the new node
                                            e.setColour(getActive());
                                            update(getGraphic());
                                            pause();
                                            getPq().add(i + 1, newNode); // add the new node after the current
                                            break; // stop the loop as we have found its place
                                        } else if (i == 0 && getPq().get(i).distance > newNode.distance) {
                                            e.setColour(getActive());
                                            update(getGraphic());
                                            pause();
                                            getPq().add(0, newNode); // add to the start of the list
                                        }
                                    }
                                } else { // if there isn't then just add the node to the list
                                    e.setColour(getActive());
                                    update(getGraphic());
                                    pause();
                                    getPq().add(newNode);
                                }
                            } else {
                                printSTD("\t ## In PQ at Lower Weight " + getNl()[nID].distance + " ##\n");
                                pause();
                            }
                        } else {
                            printSTD("\t ## Already Visited ##\n");
                            pause();
                        }
                        // uncommenting these lines will make the program print out the PQ at each step
                        // as this is something that Ender wants us to do at somepoint, i've made sure it works now...
                        clearPQ();
                        pqList = getPq().listIterator();
                        if (pqList.hasNext()) {
                            while (pqList.hasNext()) {
                                nodeList nn = pqList.next();
                                printPQ("<" + nn.node.getName() + " : " + nn.distance + "> ");
                            }
                        } else {
                            printPQ("<empty>");
                        }
                        //e.setColour(visited);
                    }
                    current.node.setColour(getVisited());
                    pause();
                } else {
                    // if this node has no edges then remove it from the node list
                    printSTD("\t ## Node " + current.node.getName() + " Removed: No Edges ##\n");
                    for (int i = 0; i < getNl().length; i++) {
                        if (getNl()[i].equals(current)) {
                            getNl()[i] = null;
                            break;
                        }
                    }
                }
            }

            update(getGraphic());
            if (!pq.isEmpty()) setNextNode(getPq().getFirst().node.getName());
            if (!pq.isEmpty()) {
                pause(getPauseDUR() * 2, true);
            } else {
                pause(getPauseDUR() * 2, false);
            }
            clearSTD();
        }
        printSTD(" -- End of PQ\n");

        LinkedList<GraphNode> newG = new LinkedList<GraphNode>();

        for (int i = 0; i < getNl().length; i++) {
            if (getNl()[i] != null && !nl[i].visited) {
                newG.add(getNl()[i].node);
                getNl()[i] = null;
            }
        }
        printSTD("\n");
        if (newG.size() > 0) {
            printSTD(" -- More Nodes exist, Ignoring\n\n");
            trace(getNl());
            //run(newG);
        } else {
            trace(getNl());
        }//*/
        getGui().algoFinished();
        getSTDout().setVisible(false);
        getPQout().setVisible(false);
    }

    /**
     * @return the nl
     */
    public nodeList[] getNl() {
        return nl;
    }

    /**
     * @param nl the nl to set
     */
    public void setNl(nodeList[] nl) {
        this.nl = nl;
    }

    /**
     * @return the pq
     */
    public LinkedList<nodeList> getPq() {
        return pq;
    }

    /**
     * @param pq the pq to set
     */
    public void setPq(LinkedList<nodeList> pq) {
        this.pq = pq;
    }
}
