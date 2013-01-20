package GUI.graphIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 *
 */

import GUI.Edge;
import GUI.GUI;
import GUI.Graph;
import GUI.GraphNode;
import java.io.*;

/**
 *
 * @author Tom
 */
public class SimpleIO {

    private final String GRAPH = "graph";
    private final String NODE = "node";
    private final String EDGE = "edge";

    /**
     * @param args the command line arguments
     *
     * public static void main(String[] args) throws FileNotFoundException,
     * IOException { // TODO code application logic here SimpleIO io = new
     * SimpleIO(); io.loadFile("demo.graphs");
    }//
     */
    /**
     * Saves a graph into a file, named the same as the graph
     *
     * @param graph
     * @return true if the program succeeds at saving the given graph
     */
    public boolean saveGraph(Graph graph) {
        // check if a file of this name already exists, if so ask if the user wants to overwrite it, if not then offer to rename the graph.
        try (BufferedWriter outputStream = new BufferedWriter(new FileWriter("save/"+graph.getGraphName().replace(" ", "_")+".graph", true))) {
            String line;
            // write the graph line
            line = "<graph " + graph.getGraphName() + "> algo(" + graph.Algorithm.name() + ") graph(" + graph.GraphType.name() + ") input(" + graph.InputType.name() + ") node(" + graph.NodeType.name() + ")";
            if (graph.advancedMode) {
                line += " advancedMode()";
            }
            if (graph.teachingMode) {
                line += " teachingMode()";
            }

            line += "\n\n";
            outputStream.append(line);
            // write each node in the graph
            for (GraphNode gn : graph.getNodes()) {
                line = "\t<node>";
                if (gn.isNamed) {
                    line = line + " name(" + gn.getName() + ")";
                }
                line = line + " location(" + gn.getX() + "," + gn.getY() + ") size(" + gn.getWidth() + ")";
                if (graph.rootNode == gn) {
                    line += " rootNode()";
                } else if (graph.endNode == gn) {
                    line += " endNode()";
                }
                outputStream.append(line + "\n");
            }

            line = "\n";
            outputStream.append(line);
            // write each edge to file
            for (Edge e : graph.getEdges()) {
                GraphNode node0 = e.node[0];
                GraphNode node1 = e.node[1];
                int n0Index = 0, n1Index = 0;
                for (int i = 0; i < graph.getLastNodeIndex(); i++) {
                    if(node0 == graph.getNodes().get(i)){
                        n0Index = i;
                    } else if (node1 == graph.getNodes().get(i)){
                        n1Index = i;
                    }
                }
                line = "\t<edge> node0(" + n0Index + ") node1(" + n1Index + ") weight(" + e.getWeight() + ")";
                outputStream.append(line  + "\n\n");
            }

            line = "</graph>\n";
            outputStream.append(line);
        } catch (IOException ioError) {
        }
        return true;
    }

    /**
     * This function recursively searches for .graphs files in the current
     * directory
     *
     * @param fileLoc
     * @return if the File was loaded correctly
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Graph loadFile(String fileLoc) throws FileNotFoundException, IOException {

        try (BufferedReader inputStream = new BufferedReader(new FileReader(fileLoc))) {

            String line;
            Graph workingGraph = new Graph();
            int i;
            line = inputStream.readLine();
            while (line != null) {
                line = line.trim();
                // now step through the line till we find an opening control character
                if (line.length() != 0) {
                    // we need to find the next whitespace character or '>' so we can find out what type of input this is
                    for (i = 1; i < line.length(); i++) {
                        if (line.charAt(i) == ' ' || line.charAt(i) == '>') {
                            break;
                        }
                    }// this should've found the end of the statement so we can now get the information we want
                    String command = line.substring(1, i);
                    line = line.substring(i, line.length());
                    //System.out.println(command);
                    // we now have the command that for the statement
                    if (command.matches(GRAPH)) {
                        String graphName = line.substring(0, line.indexOf('>'));
                        if (graphName.trim().length() == 0) { // this shouldn't happen, but you never know...
                            graphName = "Untitled";
                        }
                        workingGraph.changeName(graphName.trim()); // trim the name to remove any extra white space... obviously
                        line = line.substring(line.indexOf('>') + 2, line.length());
                        line.trim();
                        //System.out.println("Graph Name: "+ currentGraph.getName());
                        // then this is the start of the graph, process information about this graph
                        while (line.length() != 0) { // recurse through the rest of the line
                            int end = line.indexOf('('); // find the location of the next open bracket
                            command = line.substring(0, end);
                            command.trim();
                            //System.out.println(command);
                            if (command.matches("algo")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                workingGraph.setAlgoType(GUI.algoType.valueOf(value));
                            } else if (command.matches("graph")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                workingGraph.setGraphType(GUI.graphType.valueOf(value));
                            } else if (command.matches("input")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                workingGraph.setInputType(GUI.inputType.valueOf(value));
                            } else if (command.matches("node")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                workingGraph.setNodeType(GUI.nodeType.valueOf(value));
                            } else if (command.matches("teachingMode")) {
                                workingGraph.teachingMode = true;
                            } else if (command.matches("advancedMode")) {
                                workingGraph.advancedMode = true;
                            }
                            try {
                                line = line.substring(end + 2, line.length());
                                line.trim();
                                //System.out.println(line);
                            } catch (StringIndexOutOfBoundsException ignore) {
                                break;
                            }
                        }
                    } else if (command.matches(NODE)) {
                        // iterate through the rest of the line to get all of the information on the node
                        line = line.substring(line.indexOf('>') + 2, line.length());
                        line.trim();
                        int x = 6, y = 6, size = 72;
                        boolean locationFound = false, isRootNode = false, isEndNode = false, nameChanged = false;
                        String name = "N" + workingGraph.getLastNodeIndex();
                        //System.out.println("Graph Name: "+ currentGraph.getName());
                        // then this is the start of the graph, process information about this graph
                        while (line.length() != 0) { // recurse through the rest of the line
                            int end = line.indexOf('('); // find the location of the next open bracket
                            command = line.substring(0, end);
                            command.trim();
                            //System.out.println(command);
                            if (command.matches("location")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                x = Integer.parseInt(value.substring(0, value.indexOf(',')));
                                try {
                                    y = Integer.parseInt(value.substring(value.indexOf(',') + 1, value.length()));
                                } catch (NumberFormatException ignore) {
                                    y = Integer.parseInt(value.substring(value.indexOf(',') + 2, value.length()));
                                }
                                end = newEnd;
                                locationFound = true;
                                //System.out.println("X: " + x + ", Y: " + y);
                            } else if (command.matches("size")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                size = Integer.parseInt(value);
                                //System.out.println("Size is: "+size);
                            } else if (command.matches("name")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                name = value;
                                nameChanged = true;
                                //System.out.println("Name of node is: "+name);
                            } else if (workingGraph.getAlgoType() == GUI.algoType.dijkstras && command.matches("rootNode")) {
                                isRootNode = true;
                                isEndNode = false;
                            } else if (workingGraph.getAlgoType() == GUI.algoType.dijkstras && command.matches("endNode")) {
                                isEndNode = true;
                                isRootNode = false;
                            }
                            try {
                                line = line.substring(end + 2, line.length());
                                line.trim();
                                //System.out.println(line);
                            } catch (StringIndexOutOfBoundsException ignore) {
                                break;
                            }
                        }
                        if (locationFound) {
                            GraphNode newNode = new GraphNode(name, x, y, size);
                            if (nameChanged) {
                                newNode.isNamed = true;
                            }
                            workingGraph.addNode(newNode);
                            if (isRootNode) {
                                //System.out.println("Node "+newNode.getName()+" set as Root");
                                workingGraph.rootNode = newNode;
                            } else if (isEndNode) {
                                //System.out.println("Node "+newNode.getName()+" set as End");
                                workingGraph.endNode = newNode;
                            }
                        } else {
                            System.out.println("Node Creation Error: No Location Defined");
                        }
                    } else if (command.matches(EDGE)) {
                        // iterate through the rest of the line to get the rest of the info on the edge
                        line = line.substring(line.indexOf('>') + 2, line.length());
                        line.trim();
                        int node0 = 0, node1 = 0;
                        double weight = 0;
                        boolean node0Found = false, node1Found = false, weightFound = false;
                        //System.out.println("Graph Name: "+ currentGraph.getName());
                        // then this is the start of the graph, process information about this graph
                        while (line.length() != 0) { // recurse through the rest of the line
                            int end = line.indexOf('('); // find the location of the next open bracket
                            command = line.substring(0, end);
                            command.trim();
                            //System.out.println(command);
                            if (command.matches("node0")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                node0 = Integer.parseInt(value);
                                end = newEnd;
                                node0Found = true;
                                //System.out.println("Start Node: " + node0);
                            } else if (command.matches("node1")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                node1 = Integer.parseInt(value);
                                node1Found = true;
                                //System.out.println("End Node: "+node1);
                            } else if (command.matches("weight")) {
                                int newEnd = line.indexOf(')');
                                String value = line.substring(end + 1, newEnd);
                                end = newEnd;
                                weight = Double.parseDouble(value);
                                weightFound = true;
                                //System.out.println("Edge Weight is: "+weight);
                            }
                            try {
                                line = line.substring(end + 2, line.length());
                                line.trim();
                                //System.out.println(line);
                            } catch (StringIndexOutOfBoundsException ignore) {
                                break;
                            }
                        }
                        if (node0Found && node1Found && weightFound) {
                            workingGraph.addEdge(node0, node1, weight);
                        } else {
                            System.out.println("Incomplete Edge! Please ensure that all data required\n"
                                    + "is defined in the file (Start Node, End Node, and Weight)");
                        }
                    }
                }
                line = inputStream.readLine();
                if(line.equals("</graph>")){
                    return workingGraph;
                }
            }
            return workingGraph;
        }
    }
}
