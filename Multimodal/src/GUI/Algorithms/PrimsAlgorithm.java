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
 * Algorithm: Prims' -- Takes the list of Edges and GraphNodes, returns a
 * minimum spanning tree for this set...
 *
 * @version 1.0.1
 * Rewrite to enable interface with new ProcessedGraph + sorting algorithms
 *
 * @author tab00u
 * @version 0.9.8
 * First finished version
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

        while (!pq.isEmpty()) {
            nodeList current = getPq().removeFirst();

            if (!nl[current.ID].visited) { // checks the node that has been taken out of the pq
                current.node.setColour(getActive());
                Iterator<Edge> edgeIter = getNl()[current.ID].node.getEdges().iterator(); // make an iterator out of the edges assigned to the node.
                if (edgeIter.hasNext()) {
                    while (edgeIter.hasNext()) {
                        Edge e = edgeIter.next();
                        int nID = findNodeID(e.getAdjoiningNode(current.node));
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
                                                oldE2.setColour(Color.WHITE); // if the edge of the old pred is the same edge as the one from the new edge then set it to be black
                                                break;
                                            }
                                        }
                                    }
                                    getPq().remove(getNl()[nID]);
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
                                            getPq().add(i + 1, newNode); // add the new node after the current
                                            break; // stop the loop as we have found its place
                                        } else if (i == 0 && getPq().get(i).distance > newNode.distance) {
                                            e.setColour(getActive());
                                            getPq().add(0, newNode); // add to the start of the list
                                        }
                                    }
                                } else { // if there isn't then just add the node to the list
                                    e.setColour(getActive());
                                    getPq().add(newNode);
                                }
                            }
                        }
                    }
                    current.node.setColour(getVisited());
                } else {
                    // if this node has no edges then remove it from the node list
                    for (int i = 0; i < getNl().length; i++) {
                        if (getNl()[i].equals(current)) {
                            getNl()[i] = null;
                            break;
                        }
                    }
                }
            }

            update(getGraphic());
            if (!pq.isEmpty()) {
                setNextNode(getPq().getFirst().node.getName());
            }
        }
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
