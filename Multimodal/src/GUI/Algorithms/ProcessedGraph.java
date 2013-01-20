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
public class ProcessedGraph {
    private LinkedList<GraphStage> graph = new LinkedList<>();


    public ProcessedGraph(){

    }

    /**
     * Adds a new GraphStage onto the end of the list
     * @param newStage - the overall graph
     * @param activeNode - the node which is currently being looked at
     * @param activeEdge - the edge which is currently being looked at
     * @param visitedNodes - a list of nodes that have already been visited
     * @param visitedEdges - a list of edges that have already been visited
     * @param queueText - the current priority queue for this graph
     * @param infoText - just stuff about what is currently happening
     * @return true on completion
     */
    public boolean addStage(
            Graph newStage,
            GraphNode activeNode,
            Edge activeEdge,
            LinkedList<GraphNode> visitedNodes,
            LinkedList<Edge> visitedEdges,
            String queueText,
            String infoText
            ){

        GraphStage temp = new GraphStage(newStage);
        temp.setActiveNode(activeNode);
        temp.setActiveEdge(activeEdge);
        temp.setVisitedEdges(visitedEdges);
        temp.setVisitedNodes(visitedNodes);
        temp.setInfoText(infoText);
        temp.setQueueText(queueText);
        graph.addLast(temp);
        return true;
    }

    /**
     * @return the graph
     */
    public LinkedList<GraphStage> getGraph() {
        return graph;
    }

    /**
     * @param graph the graph to set
     */
    public void setGraph(LinkedList<GraphStage> graph) {
        this.graph = graph;
    }

}
