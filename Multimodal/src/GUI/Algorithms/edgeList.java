package GUI.Algorithms;


import GUI.Edge;

/**
 * Version 0.1: Initial Code, allows the Algorithm to mark edges as expanded or not
 * @author tab00u
 * @version 0.1
 *
 */
public class edgeList {

    public boolean expanded = false; // no node is initialy visited
    public int ID = 0;
    public Edge edge = null; // the node associated with this listing

    public edgeList(Edge e, int id){
        edge = e;
        ID = id;
    }
}
