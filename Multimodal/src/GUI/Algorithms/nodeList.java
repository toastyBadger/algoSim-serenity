package GUI.Algorithms;


import GUI.GraphNode;

/**
 * Version 0.1: Initial Code, allows the Algorithm to mark nodes as visited or not
 * Version 0.2: added precedentID to point to the id of the previous node, might be useful at some point
 * @author tab00u
 * @version 0.2
 *
 */
public class nodeList {

    public boolean visited = false; // no node is initialy visited
    public double distance = Double.MAX_VALUE; // total distance from start node
    public int ID = 0;
    public int precedentID = -1; // the node that the current one is preceded by
    public GraphNode node = null; // the node associated with this listing

    public nodeList(GraphNode nde, int id){
        node = nde;
        ID = id;
    }
}
