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
 * Algorithm: Dijkstra's -- Takes the list of Edges and GraphNodes, returns a
 * minimum spanning tree for this set...
 *
 * @author tab00u
 * @version 0.27
 */
public class DsAlgorithm extends AlgorithmBase {

    private static long serialVersionUID = 2l;

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
    private nodeList[] nl = null;
    private LinkedList<nodeList> pq = new LinkedList<>();
    private boolean dw = isDirected();
    private boolean endFound = false;

    public DsAlgorithm(Graph graph, Graphics grp, TextArea out, TextArea pqout, GUI gui) {
        super(graph, grp, out, pqout, gui);
        setRootNode(graph.rootNode);
        setEndNode(graph.endNode);

    }

    /**
     * Finds the ID of the Node passed to it
     *
     * @param node The node whose ID you wish to find from the nodeList
     * @return the ID of the node Passed to it
     */
    protected int findNodeID(GraphNode node) {
        for (int i = 0; i < getNl().length; i++) {
            if (getNl()[i].node.equals(node)) { // compares node hashcodes
                return getNl()[i].ID;
            }
        }
        return -1; // -1 will never exist... and will actually break the program...
    }

    @Override
    public void run() {
        if (isDw() && getRootNode() != null && getEndNode() != null) {
            run(getNodes());
        } else if (isDw()) {
            JOptionPane.showMessageDialog(this, "Please set the Start Node and End Node by clicking on it", "ERROR: Algorithm Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (!getNodes().isEmpty()) {
                run(getNodes());
            } else {
                JOptionPane.showMessageDialog(this, "No Node Graph Found", "ERROR: Algorithm Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void run(LinkedList<GraphNode> g) {
        if (!isDw()) {
            int start = 0;
            pause();
            //boolean found=false;

            int n = g.size(); // gets the size of the list
            setNl(new nodeList[n]); // makes the list the same size as the list
            for (int i = 0; i < n; i++) { // steps through each node and assigns it to the nodeList
                getNl()[i] = new nodeList(getNodes().get(i), i); // also assings each node an id.
                nl[i].distance = Integer.MAX_VALUE;
                if (getNl()[i].node == getRootNode()) {
                    start = i;
                }
            }
            nl[start].distance = 0;

            //initialise linked List for the Priority Queue
            setPq(new LinkedList<nodeList>());
            getPq().add(getNl()[start]);

            printSTD("Starting");
            pause(100, false);
            for (int i = 0; i < 10; i++) {
                printSTD(".");
                pause(getPauseDUR() / 10, false);
            }
            printSTD("\n -- Root Node is: " + getRootNode().getName() + "\n");

            while (!pq.isEmpty()) {
                setCurrent(getPq().removeFirst());
                clearPQ();
                if (getCurrent().node != getEndNode()) {
                    Iterator<nodeList> pqList = getPq().listIterator();
                    if (pqList.hasNext()) {
                        while (pqList.hasNext()) {
                            nodeList nn = pqList.next();
                            printPQ("<" + nn.node.getName() + " : " + nn.distance + "> ");
                        }
                    } else {
                        printPQ("<empty>");
                    }

                    printSTD("-- Expanding Node " + getCurrent().node.getName() + ":\n");
                    if (!nl[getCurrent().ID].visited) { // checks the node that has been taken out of the pq
                        getCurrent().node.setColour(getActive());
                        update(getGraphic());
                        pause();
                        Iterator<Edge> edgeIter = getNl()[getCurrent().ID].node.getEdges().iterator(); // make an iterator out of the edges assigned to the node.
                        if (edgeIter.hasNext()) {
                            while (edgeIter.hasNext()) {
                                Edge e = edgeIter.next();
                                int nID = findNodeID(e.getAdjoiningNode(getCurrent().node));
                                printSTD("\t -- Edge Found to Node " + getNl()[nID].node.getName() + " Path Cost: " + (e.getWeight() + getCurrent().distance));
                                double eWeight = e.getWeight();
                                nl[getCurrent().ID].visited = true;
                                if (!nl[nID].visited) {
                                    if (getNl()[nID].distance > (eWeight + getCurrent().distance)) {
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
                                                        oldE2.setColour(Color.DARK_GRAY);
                                                        // if the edge of the old pred is the same edge as the one from the new edge then set it to be black
                                                        update(getGraphic());
                                                        //pause();
                                                        break;
                                                    }
                                                }
                                            }
                                            getPq().remove(getNl()[nID]);
                                            printSTD("\t ## Re-added to PQ at lower Path Cost: ");
                                        } else {
                                            printSTD("\t ## Added to PQ at: ");
                                        }

                                        nodeList newNode = getNl()[nID];
                                        newNode.distance = eWeight + getCurrent().distance;
                                        newNode.precedentID = getCurrent().ID; // set this to be the id of the node from which we came :D
                                        printSTD(newNode.distance + " ##\n");

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
                                        printSTD("\t ## Already in PQ at Lower Path Cost: " + getNl()[nID].distance + " ##\n");
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
                            getCurrent().node.setColour(getVisited());
                            pause();
                        } else {
                            // if this node has no edges then remove it from the node list
                            printSTD("\t ## Node " + getCurrent().node.getName() + " Removed: No Edges ##\n");
                            for (int i = 0; i < getNl().length; i++) {
                                if (getNl()[i].equals(getCurrent())) {
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
                    if (!pq.isEmpty()) {
                        pause(getPauseDUR() * 2, true);
                    } else {
                        pause(getPauseDUR() * 2, false);
                    }
                    clearSTD();
                } else {

                    printSTD("-- Found Node " + getCurrent().node.getName() + ", it reports that it is the end node! ");

                    pause(getPauseDUR(), false);

                    clearSTD();
                    setEndID(getCurrent().ID);
                    printSTD(" -- End Node has been found with a total Path cost of " + getNl()[getEndID()].distance + "! ending algorithm\n");
                    pause();

                    //printSTD(" -- End Node has been found! ending algorithm\n");
                    // this next bit is here as it is too hard to replicate into trace
                    getEndNode().setColour(Color.CYAN);
                    setEndID(getCurrent().ID);
//                    if (current.precedentID != -1) {
//                        LinkedList<Edge> edgeList = endNode.getEdges();
//                        for (Edge edge : edgeList) {
//                            if (edge.getAdjoiningNode(endNode).equals(nl[current.precedentID].node)) {
//
//                                edge.setColour(Color.green);
//
//                            }
//                        }
//                    }
                    setEndFound(true);
                    break;
                }
            } // end While(!pq.isEmpty());
            printSTD(" -- End of PQ\n");

            LinkedList<GraphNode> newG = new LinkedList<GraphNode>();

//            for (int i = 0; i < nl.length; i++) {
//                if (nl[i] != null && !nl[i].visited) {
//                    newG.add(nl[i].node);
//                    nl[i] = null;
//                }
//            }
            printSTD("\n");
            getGui().repaintBlack();
            if (newG.size() > 0 && !isEndFound()) {
                printSTD(" -- End Not Found in this graph, Cannot Complete!");
                traceD(getNl());
                //run(newG); this is commented out now as if it reaches this point it shouldn't carry on
            } else {
                traceD(getNl());
            }






        } else {







            int start = 0;
            pause();
            //boolean found=false;

            int n = g.size(); // gets the size of the list
            setNl(new nodeList[n]); // makes the list the same size as the list
            for (int i = 0; i < n; i++) { // steps through each node and assigns it to the nodeList
                getNl()[i] = new nodeList(getNodes().get(i), i); // also assings each node an id.
                nl[i].distance = Integer.MAX_VALUE;
                if (getNl()[i].node == getRootNode()) {
                    start = i;
                }
            }
            nl[start].distance = 0;

            //initialise linked List for the Priority Queue
            setPq(new LinkedList<nodeList>());
            getPq().add(getNl()[start]);

            printSTD("Starting");
            pause(100, false);
            for (int i = 0; i < 10; i++) {
                printSTD(".");
                pause(getPauseDUR() / 10, false);
            }
            printSTD("\n -- Root Node is: " + getRootNode().getName() + "\n");

            while (!pq.isEmpty()) {
                setCurrent(getPq().removeFirst());
                clearPQ();
                if (getCurrent().node != getEndNode()) {
                    Iterator<nodeList> pqList = getPq().listIterator();
                    if (pqList.hasNext()) {
                        while (pqList.hasNext()) {
                            nodeList nn = pqList.next();
                            printPQ("<" + nn.node.getName() + " : " + nn.distance + "> ");
                        }
                    } else {
                        printPQ("<empty>");
                    }

                    printSTD("-- Expanding Node " + getCurrent().node.getName() + ":\n");
                    if (!nl[getCurrent().ID].visited) { // checks the node that has been taken out of the pq
                        getCurrent().node.setColour(Color.yellow);
                        update(getGraphic());
                        pause();
                        Iterator<Edge> edgeIter = getNl()[getCurrent().ID].node.getEdges().iterator(); // make an iterator out of the edges assigned to the node.
                        if (edgeIter.hasNext()) {
                            while (edgeIter.hasNext()) {
                                Edge e = edgeIter.next();
                                if (e.node[0] == getCurrent().node) {
                                    int nID = findNodeID(e.getAdjoiningNode(getCurrent().node));
                                    printSTD("\t -- Edge Found to Node " + getNl()[nID].node.getName() + " Path Cost: " + (e.getWeight() + getCurrent().distance));
                                    double eWeight = e.getWeight();
                                    nl[getCurrent().ID].visited = true;
                                    if (!nl[nID].visited) {
                                        if (getNl()[nID].distance > (eWeight + getCurrent().distance)) {
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

                                                            oldE2.setDColour(Color.DARK_GRAY);
                                                            // if the edge of the old pred is the same edge as the one from the new edge then set it to be black
                                                            update(getGraphic());
                                                            //pause();
                                                            break;
                                                        }
                                                    }
                                                }
                                                getPq().remove(getNl()[nID]);
                                                printSTD("\t ## Re-added to PQ at Lower Cost: ");
                                            } else {
                                                printSTD("\t ## Added to PQ at: ");
                                            }

                                            nodeList newNode = getNl()[nID];
                                            newNode.distance = eWeight + getCurrent().distance;
                                            newNode.precedentID = getCurrent().ID; // set this to be the id of the node from which we came :D
                                            printSTD(newNode.distance + " ##\n");

                                            // this next segment sorts the PQ and makes sure it is in order
                                            if (getPq().size() != 0) { // check to make sure that there is actually something in the pq
                                                for (int i = getPq().size() - 1; i >= 0; i--) { // if there is...
                                                    if (getPq().get(i).distance <= newNode.distance) {
                                                        // if the current node in the list has a weight lower than, or equal to the new node

                                                        e.setDColour(Color.red);

                                                        update(getGraphic());
                                                        pause();
                                                        getPq().add(i + 1, newNode); // add the new node after the current
                                                        break; // stop the loop as we have found its place
                                                    } else if (i == 0 && getPq().get(i).distance > newNode.distance) {

                                                        e.setDColour(Color.red);

                                                        update(getGraphic());
                                                        pause();
                                                        getPq().add(0, newNode); // add to the start of the list
                                                    }
                                                }
                                            } else { // if there isn't then just add the node to the list

                                                e.setDColour(Color.red);

                                                update(getGraphic());
                                                pause();
                                                getPq().add(newNode);
                                            }
                                        } else {
                                            printSTD("\t ## In PQ at Lower Path Cost: " + getNl()[nID].distance + " ##\n");
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
                                }
                                getCurrent().node.setColour(Color.red);
                                pause();
                            }
                        } else {
                            // if this node has no edges then remove it from the node list
                            printSTD("\t ## Node " + getCurrent().node.getName() + " Removed: No Edges ##\n");
                            for (int i = 0; i < getNl().length; i++) {
                                if (getNl()[i].equals(getCurrent())) {
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
                    if (!pq.isEmpty()) {
                        pause(getPauseDUR() * 2, true);
                    } else {
                        pause(getPauseDUR() * 2, false);
                    }
                    clearSTD();
                } else {
                    printSTD("-- Found Node " + getCurrent().node.getName() + ", it reports that it is the end node! ");

                    pause();

                    clearSTD();
                    setEndID(getCurrent().ID);
                    printSTD(" -- End Node has been found with a total Path cost of " + getNl()[getEndID()].distance + "! ending algorithm\n");
                    pause();
                    // this next bit is here as it is too hard to replicate into trace
                    getEndNode().setColour(Color.CYAN);
                    if (getCurrent().precedentID != -1) {
                        LinkedList<Edge> edgeList = getEndNode().getEdges();
                        for (Edge edge : edgeList) {
                            if (edge.getAdjoiningNode(getEndNode()).equals(getNl()[getCurrent().precedentID].node)) {

                                edge.setDColour(Color.green);

                            }
                        }
                    }
                    setEndFound(true);
                    break;
                }
            } // end While(!pq.isEmpty());
            printSTD(" -- End of PQ\n");

            LinkedList<GraphNode> newG = new LinkedList<GraphNode>();

//            for (int i = 0; i < nl.length; i++) {
//                if (nl[i] != null && !nl[i].visited) {
//                    newG.add(nl[i].node);
//                    nl[i] = null;
//                }
//            }
            printSTD("\n");
            getGui().repaintBlack();
            if (newG.size() > 0 && !isEndFound()) {
                printSTD(" -- End Not Found in this graph, Cannot Complete!");
                pause(getPauseDUR() * 3, false);
                traceD(getNl());
                //run(newG); this is commented out now as if it reaches this point it shouldn't carry on
            } else {
                traceD(getNl());
            }


        }
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

    /**
     * @return the dw
     */
    public boolean isDw() {
        return dw;
    }

    /**
     * @param dw the dw to set
     */
    public void setDw(boolean dw) {
        this.dw = dw;
    }

    /**
     * @return the endFound
     */
    public boolean isEndFound() {
        return endFound;
    }

    /**
     * @param endFound the endFound to set
     */
    public void setEndFound(boolean endFound) {
        this.endFound = endFound;
    }
}