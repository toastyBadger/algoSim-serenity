package GUI;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Tom
 */
class Keyboard extends JFrame {

    // all key but BSP
    String qwerty = "QWERTYUIOPASDFGHJKLZXCVBNM ";
    JTextField field;
    nodeOptions node;

    Keyboard(nodeOptions option) {
        super("Please Enter a New Name: ");
        node = option;
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEADING));

        // create TextField
        field = new JTextField(18);
        field.setFont(new Font("Tahoma", Font.PLAIN, 32));
        
        contentPane.add(field);
        field.setText(node.SelectedNode.getName());

        // create buttons
        for (int i = 0; i < qwerty.length(); i++) {
            String label = "" + qwerty.charAt(i);
            contentPane.add(new MyButton(this, label));
        }
        contentPane.add(new BSPButton(this));
        contentPane.setPreferredSize(new Dimension(512, 256));
        add(contentPane);
        
        setResizable(false);
        pack(); 
        
        setAlwaysOnTop(true);
        
        addWindowListener(new KeyboardListener(node, this));        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}


class KeyboardListener implements WindowListener {
    
    nodeOptions node = null;
    Keyboard keys = null;
    
    public KeyboardListener(nodeOptions node, Keyboard key){
        this.node = node;
        keys = key;
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
        node.SelectedNode.isNamed = true;
        node.SelectedNode.setName(keys.field.getText().trim());
        node.refresh();
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

class MyButton extends JButton implements ActionListener {
    
    Keyboard keyboard;

    // constructor
    MyButton(Keyboard frame, String name) {
        super(name);
        if(name.equals(" ")){
            this.setPreferredSize(new Dimension(256, 48));
        }else{
            this.setPreferredSize(new Dimension(48, 48));
        }
        keyboard = frame;
        addActionListener(this);
    }
    // button was hit

    public void actionPerformed(ActionEvent e) {
        keyboard.field.setText(keyboard.field.getText() + getText());    // append to field my label
    }
}

class BSPButton extends JButton implements ActionListener {

    Keyboard keyboard;
    
    BSPButton(Keyboard frame) {
        super("BSP");
        keyboard = frame;
        addActionListener(this);
        this.setPreferredSize(new Dimension(64, 48));
    }

    public void actionPerformed(ActionEvent e) {
        String text = keyboard.field.getText();
        if (text.length() == 0) {
            return;
        }
        keyboard.field.setText(text.substring(text.length()));
    }
}
