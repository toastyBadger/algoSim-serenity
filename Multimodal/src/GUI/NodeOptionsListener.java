package GUI;


import GUI.GUI;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tom
 */
public class NodeOptionsListener implements WindowListener {
    
    GUI gui = null;
    
    public NodeOptionsListener(GUI gui){
        super();
        this.gui = gui;
    }

    @Override
    public void windowOpened(WindowEvent we) {        
    }

    @Override
    public void windowClosing(WindowEvent we) {        
        gui.hideNodeOptions();
        we.getWindow().dispose();
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }
    
}
