/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Algorithms;

import GUI.Edge;
import GUI.Graph;
import GUI.GraphNode;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class GraphStage {
    private Graph graph = new Graph();

    private LinkedList<GraphNode> VisitedNodes; // list of all visited nodes, these will be coloured blue
    private LinkedList<Edge> VisitedEdges;      // list of all visited edges, these will also be blue
    private GraphNode ActiveNode;               // the currently active node, this will be yellow
    private Edge ActiveEdge;                    // the currently actuve edge, this will be yellow as well

    private String QueueText;                   // Holds info on which nodes are curretnly in the queue
    private String InfoText;                    // tells the user what is currently being looked at by the algorithm

    public GraphStage(Graph graph){
        this.graph = graph;
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
     * @return the VisitedNodes
     */
    public LinkedList<GraphNode> getVisitedNodes() {
        return VisitedNodes;
    }

    /**
     * @param VisitedNodes the VisitedNodes to set
     */
    public void setVisitedNodes(LinkedList<GraphNode> VisitedNodes) {
        this.VisitedNodes = VisitedNodes;
    }

    /**
     * @return the VisitedEdges
     */
    public LinkedList<Edge> getVisitedEdges() {
        return VisitedEdges;
    }

    /**
     * @param VisitedEdges the VisitedEdges to set
     */
    public void setVisitedEdges(LinkedList<Edge> VisitedEdges) {
        this.VisitedEdges = VisitedEdges;
    }

    /**
     * @return the ActiveNode
     */
    public GraphNode getActiveNode() {
        return ActiveNode;
    }

    /**
     * @param ActiveNode the ActiveNode to set
     */
    public void setActiveNode(GraphNode ActiveNode) {
        this.ActiveNode = ActiveNode;
    }

    /**
     * @return the ActiveEdge
     */
    public Edge getActiveEdge() {
        return ActiveEdge;
    }

    /**
     * @param ActiveEdge the ActiveEdge to set
     */
    public void setActiveEdge(Edge ActiveEdge) {
        this.ActiveEdge = ActiveEdge;
    }

    /**
     * @return the QueueText
     */
    public String getQueueText() {
        return QueueText;
    }

    /**
     * @param QueueText the QueueText to set
     */
    public void setQueueText(String QueueText) {
        this.QueueText = QueueText;
    }

    /**
     * @return the InfoText
     */
    public String getInfoText() {
        return InfoText;
    }

    /**
     * @param InfoText the InfoText to set
     */
    public void setInfoText(String InfoText) {
        this.InfoText = InfoText;
    }

}
