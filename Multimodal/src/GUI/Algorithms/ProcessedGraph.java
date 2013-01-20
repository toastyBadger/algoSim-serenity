/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Algorithms;

import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class ProcessedGraph {
    private LinkedList<GraphStage> graph = new LinkedList<>();



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
