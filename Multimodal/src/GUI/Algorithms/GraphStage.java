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

    LinkedList<GraphNode> VisitedNodes; // list of all visited nodes, these will be coloured blue
    LinkedList<Edge> VisitedEdges;      // list of all visited edges, these will also be blue
    GraphNode ActiveNode;               // the currently active node, this will be yellow
    Edge ActiveEdge;                    // the currently actuve edge, this will be yellow as well

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

}
