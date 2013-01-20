package GUI.graphIO;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import GUI.GUI;
import GUI.Graph;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *  this is a simple "interface" between the GUI and simpleIO
 * @author gpProject
 */
public class graphIO {
    private String line = null;
    private LinkedList<Graph> AllGraphs = new LinkedList<>();
    private SimpleIO io = new SimpleIO();
    private Image img;

    /**
     *
     * @param img
     */
    public graphIO (final Image img){
        // do something?
        this.img = img;
    }

    /**
     * Saves the current graph under a different name to the one specified earlier
     * @param graph
     * @param name
     * @return
     */
    public boolean saveGraph(Graph graph, String name){
        graph.changeName(name);
        return saveGraph(graph);
    }

    /**
     * Saves the current graph to disk with the name specified in the constructor if no name is specified then the user will be prompted
     * @param graph
     * @return
     */
    public boolean saveGraph(Graph graph){
        if(!graph.getGraphName().equals("Untitled")){
            return getIo().saveGraph(graph);
        } else {
            graph.changeName(JOptionPane.showInputDialog("Please enter a name for your Graph Below:"));
            return getIo().saveGraph(graph);
        }
    }

    /**
     * Automatic function to load up all of the Demo files when the program starts
     * @return
     */
    public boolean loadDemos(){
        try{
            getAllGraphs().add(getIo().loadFile("save/demos.graph"));
            GUI guI = new GUI(getAllGraphs().getFirst(), this, getImg());
        }catch (FileNotFoundException file){
            return false;
        }catch (IOException ioEx){
            return false;
        }
        return true;
    }

    public boolean loadGraph(String name){
        try{
            GUI guI = new GUI(getIo().loadFile(name), this, getImg());
        }catch (FileNotFoundException file){
            System.err.println(file);
            return false;
        }catch (IOException ioEx){
            System.err.println(ioEx);
            return false;
        }
        return true;
    }

    /**
     * @return the line
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(String line) {
        this.line = line;
    }

    /**
     * @return the AllGraphs
     */
    public LinkedList<Graph> getAllGraphs() {
        return AllGraphs;
    }

    /**
     * @param AllGraphs the AllGraphs to set
     */
    public void setAllGraphs(LinkedList<Graph> AllGraphs) {
        this.AllGraphs = AllGraphs;
    }

    /**
     * @return the io
     */
    public SimpleIO getIo() {
        return io;
    }

    /**
     * @param io the io to set
     */
    public void setIo(SimpleIO io) {
        this.io = io;
    }

    /**
     * @return the img
     */
    public Image getImg() {
        return img;
    }

    /**
     * @param img the img to set
     */
    public void setImg(Image img) {
        this.img = img;
    }
}
