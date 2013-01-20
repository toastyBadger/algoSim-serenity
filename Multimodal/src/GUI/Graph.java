package GUI;

import java.util.LinkedList;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author tab00u
 */
public class Graph {

    private LinkedList<GraphNode> nodes = new LinkedList<>();
    private LinkedList<Edge> edges = new LinkedList<>();
    private String GraphName = "Untitled";
    public GraphNode rootNode, endNode;
    
    // init these and set them as default, this WILL be changed during the Graphing progress
    public GUI.algoType Algorithm = GUI.algoType.prims;
    public GUI.graphType GraphType = GUI.graphType.uw;
    public GUI.inputType InputType = GUI.inputType.numpad;
    public GUI.nodeType NodeType = GUI.nodeType.recog;
    public boolean advancedMode = false, teachingMode = false;
    
    public Graph(){}

    public Graph(LinkedList<GraphNode> ndeIn, LinkedList<Edge> edgeIn, String name) {
        nodes = ndeIn;
        edges = edgeIn;
        GraphName = name;
    }
    
    public String getGraphName(){
        return GraphName;
    }
    
    public void setRootNode(GraphNode node){
        rootNode = node;
    }
    
    public void setEndNode(GraphNode node){
        endNode = node;
    }
    
    public void addNode(int x, int y, int size, String name){
        //System.out.println("Node "+name+" added at "+x+"*"+y);
        nodes.add(new GraphNode(name,x,y,size));
    }
    
    public void addNode(GraphNode newNode){
        //System.out.println("Node "+newNode.getName()+" added at "+newNode.getX()+"*"+newNode.getY());
        nodes.add(newNode);
    }
    
    public void update(Graph graph){
        this.Algorithm = graph.Algorithm;
        this.GraphName = graph.getName();
        this.GraphType = graph.GraphType;
        this.InputType = graph.InputType;
        this.NodeType = graph.NodeType;
        this.edges = graph.getEdges();
        this.endNode = graph.endNode;
        this.rootNode = graph.rootNode;
        this.nodes = graph.getNodes();
        this.teachingMode = graph.teachingMode;
        this.advancedMode = graph.advancedMode;
    }
    
    public int getLastNodeIndex(){
        if(nodes.isEmpty()){
            return 0;
        }else{
            return nodes.size(); 
        }
    }
    
    public void addEdge(int node0, int node1, double weight){
        //System.out.println("Edge ("+weight+") added to "+nodes.get(node0).getName() +" and "+nodes.get(node1).getName());
        edges.add(new Edge(weight, nodes.get(node0), nodes.get(node1)));
    }
    
    public String getName(){
        return GraphName;
    }
    
    public void changeName(String newName){
        GraphName = newName;
    }
    
    public void setGraphType(GUI.graphType graph){
        //System.out.println("Graph Type set to "+graph.name());
        GraphType = graph;
    }
    
    public void setAlgoType(GUI.algoType algo){
        //System.out.println("Algorithms Type set to "+algo.name());
        Algorithm = algo;
    }
    
    public void setInputType(GUI.inputType input){
        //System.out.println("Input Type set to "+input.name());
        InputType = input;
    }
    
    public void setNodeType(GUI.nodeType node){
        //System.out.println("Node Type set to "+node.name());
        NodeType = node;
    }
    
    public GUI.algoType getAlgoType(){
        return Algorithm;
    }

    public LinkedList<GraphNode> getNodes() {
        return nodes;
    }
    
    public LinkedList<Edge> getEdges() {
        return edges;
    }
    
    public void updateGraph(LinkedList<GraphNode> ndeIn, LinkedList<Edge> edgeIn) {
        nodes = ndeIn;
        edges = edgeIn;
    }
}
