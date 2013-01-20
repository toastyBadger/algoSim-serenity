package GUI.Algorithms;


import GUI.Edge;
import GUI.GraphNode;
import GUI.GUI;
import GUI.Graph;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextArea;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author tab00u
 */
public class KruskalsAlgorithm extends AlgorithmBase {

    public KruskalsAlgorithm(Graph graph, Graphics grp, TextArea out, TextArea pqout, GUI gui) {
        super(graph, grp, out, pqout, gui);
    }


}/*

    private static final long serialVersionUID = 3l;
    private nodeList[] nl = null;
    private edgeList[] el = null;

    public KruskalsAlgorithm(Graph graph, Graphics grp, TextArea out, TextArea pqout, GUI gui) {
        super(graph, grp, out, pqout, gui);
    }

    @Override
    public void run() {
        if (!edges.isEmpty() && !nodes.isEmpty()) {
            run(getEdges(), getNodes());
        } else {
            JOptionPane.showMessageDialog(this, "No Node Graph Found", "ERROR: Algorithm Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void run(LinkedList<Edge> e, LinkedList<GraphNode> g) {
        LinkedList<GraphNode> tree1=new LinkedList<GraphNode>();
        LinkedList<Edge> tree2=new LinkedList<Edge>();
        nl = new nodeList[g.size()];
        el = new edgeList[e.size()];

        int i = 0;
        for (GraphNode ns : g) {
            nl[i] = new nodeList(ns, i);
            i++;
        }

        i = 0;
        for (Edge es : e) {
            el[i] = new edgeList(es, i);
            i++;
        }

        LinkedList<edgeList> pq = new LinkedList<edgeList>();
        // sort by pq.getFirst().getWeight();
        int n = el.length;
        for (int p = 1; p < n; p++) { //2n+1
            for (i = 0; i < n - p; i++) { //2n+1
                if (el[i].edge.getWeight() > el[i + 1].edge.getWeight()) { //2n
                    //if true then excute below commands.
                    edgeList temp = el[i]; //3*po , worst case is 3*(1+2+3+...+(n-1))=3*n*(n-1)/​2
                    el[i] = el[i + 1]; //2*po , worst case is 2*(1+2+3+...+(n-1))=n*(n-1)
                    el[i + 1] = temp; //2*po , worst case is 2*(1+2+3+...+(n+1))=n*(n-1)
                    /*
                     * So in the worst case, bubble sort will cost 3.5*n^2
                     * +2.5*n+2 primitive operations. big-oh of bubble sort is
                     * O(n^2).
                     *
                }
            }
        }
        for (i = 0; i < el.length; i++) {
            pq.add(el[i]);
        }
        // pq is now a sorted list of edges...

        int nodeCounter = nl.length;
        while (!pq.isEmpty()) {
            edgeList current = pq.removeFirst();
            //current.edge.setColour(active);
            update(getGraphic());
            pause();

            int start = 0, end = 0;

            GraphNode[] ns = current.edge.getNodes();
            for (i = 0; i < nl.length; i++) {
                if (nl[i].node == ns[0]) {
                    start = i;
                } else if (nl[i].node == ns[1]) {
                    end = i;
                }
            }
            if (!nl[start].visited && !nl[end].visited) {
                //LinkedList<Edge> es = nl[end].node.getEdges();
                current.edge.setColour(getVisited());

                current.expanded = true;
                LinkedList<Edge> ess=nl[start].node.getEdges();
                for(int m=0;m<ess.size();m++){
                    Edge edge=ess.get(m);
                if(tree1.contains(edge.getAdjoiningNode(nl[start].node))||tree1.contains(nl[start].node)){

                }else{tree1.add(edge.getAdjoiningNode(nl[start].node));}
                }
                tree2.add(current.edge);
                nl[start].visited = true;
                nl[start].node.setColour(Color.BLUE);
                nl[end].visited = true;
                nl[end].node.setColour(Color.BLUE);
            } else if(nl[end].visited&&!nl[start].visited ){
                current.edge.setColour(getVisited());
                current.expanded = true;
                nl[start].visited = true;
                nl[start].node.setColour(Color.BLUE);
                nl[end].node.setColour(Color.BLUE);
                nl[end].visited = true;
                for(int m=0;m<tree2.size();m++){
                Edge edge=tree2.get(m);
                if(edge.node[0]==nl[end].node||edge.node[1]==nl[end].node){
                tree1.add(edge.getAdjoiningNode(nl[end].node));
                tree2.remove(m);
                }
                }
                if(tree1.contains(nl[start].node)||tree1.contains(nl[end].node)){
                tree1.add(nl[start].node);
                tree1.add(nl[end].node);
                }else{tree2.add(current.edge);}
        }else if(nl[start].visited&&!nl[end].visited){
                current.edge.setColour(getVisited());
                current.expanded = true;
                nl[start].visited = true;
                 nl[start].node.setColour(Color.BLUE);
                nl[end].node.setColour(Color.BLUE);
                nl[end].visited = true;
                for(int m=0;m<tree2.size();m++){
                Edge edge=tree2.get(m);
                if(edge.node[0]==nl[start].node||edge.node[1]==nl[start].node){
                tree1.add(edge.getAdjoiningNode(nl[start].node));
                tree2.remove(m);
                }
                }
                if(tree1.contains(nl[start].node)||tree1.contains(nl[end].node)){
                tree1.add(nl[start].node);
                tree1.add(nl[end].node);
                }else{tree2.add(current.edge);}
        }else if (nl[start].visited&&nl[end].visited){

                if(tree1.contains(current.edge.node[0])&&tree1.contains(current.edge.node[1])){

                }else{
                current.edge.setColour(getVisited());
                current.expanded = true;
                tree2.add(current.edge);
                tree1.add(nl[start].node);
                tree1.add(nl[end].node);
                }
        }
        }
    }

    private boolean checkCycle(GraphNode start, GraphNode end, Edge current){
        LinkedList<Edge> es = start.getEdges();
        edgeList cu=null;
        Edge shortest = null;
        for(Edge e : es){

            if(e != current){
                if(shortest == null){
                    shortest = e;

                } else if(e.getWeight() < shortest.getWeight()){
                    shortest = e;

                }
                if(e.getAdjoiningNode(start) == end)
                    return true;
            }else{break;}
            }

        if(shortest==null){return false;}

        GraphNode newNode = shortest.getAdjoiningNode(start);
        System.out.println("to node "+newNode.getName()+" from "+start.getName());
        if(newNode == end){
            System.out.println("node "+newNode.getName()+" is the end!");
            return true;
        } else {
            //if(!checkCycle(newNode, newNode, shortest))
            try{
                checkCycle(newNode, end, shortest);
            }catch(StackOverflowError ignore){
                return false;
            }
        }
        return false;
    }
}
    */
