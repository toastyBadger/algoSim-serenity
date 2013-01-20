package GUI;


import GUI.Algorithms.AlgorithmBase;
import GUI.Algorithms.DsAlgorithm;
import GUI.Algorithms.KruskalsAlgorithm;
import GUI.Algorithms.PrimsAlgorithm;
import GUI.Speech.speech;
import GUI.graphIO.graphIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.*;

/**
 * This is the main class for the program
 *
 * @author kjb00u
 * @author tab00u (little bits here and there)
 * @version 3.21
 */
public class GUI extends JPanel {

    private final Graph currentGraph = new Graph();
    private final graphIO io;
    private static final long serialVersionUID = 1376452823984663l;
    private JPanel statusBar = new JPanel();
    private GUI gui = this; // to pass to the algorithms, allows them to tell this program when they are done
    private final JFrame frame = new JFrame();
    private TextField display = new TextField("0", 15);
    public final int PQoutRowWidth = 12;
    private TextArea STDout = new TextArea("", 10, 60, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private TextArea PQout = new TextArea("", 10, PQoutRowWidth, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private Button[] buttons = new Button[16];
    private final JButton btn_RUN;
    private final JPanel btns;
    private JButton hinder = new JButton();
    private boolean changesMade = false;
    private Button[] editButtons = new Button[2];
    private Point start;
    private Point end;
    private Shape inkLine;
    private String nodeText = "Number of Nodes: ";
    private JLabel numNodes = new JLabel(nodeText);
    static speech voice;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Dimension buttonSize = new Dimension(32, 32);
    private Dimension optButtonSize = new Dimension(72, 32);
    private Graphics graphics;
    private boolean editMode = false;
    private boolean editMode2 = false;
    private boolean numpadActive = false;
    private boolean nodeEditActive = false;
    private boolean firstNum = true;
    private algoType chosenAlgo = algoType.prims;
    private graphType graph = graphType.uw;
    private nodeType node = nodeType.recog;
    private inputType input = inputType.numpad;
    private int num_Nodes = 0;
    private int nodeNameIter = 0;
    private String nodeName = "N";
    private boolean algoRunning = false;
    private GraphNode selectedNode = null;
    private Edge workingEdge = null;
    private Point loc_calc = new Point(0, 0);
    private Point loc_nodeOpt = new Point(0, 0);
    // Keypad size(w*h) is 128*160
    private Shape calRect, nodeOptRect;
    private Selection selection = null;
    private LinkedList<Point> coordinates = new LinkedList<Point>();
    private GraphNode previousNode;
    private boolean listening = false;
    private boolean recSelected = false;
    private Image img;
    private static int maxSize = 160;

    /**
     * @return the statusBar
     */
    public JPanel getStatusBar() {
        return statusBar;
    }

    /**
     * @param statusBar the statusBar to set
     */
    public void setStatusBar(JPanel statusBar) {
        this.statusBar = statusBar;
    }

    /**
     * @return the screenSize
     */
    public Dimension getScreenSize() {
        return screenSize;
    }

    /**
     * @param screenSize the screenSize to set
     */
    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * @return the buttonSize
     */
    public Dimension getButtonSize() {
        return buttonSize;
    }

    /**
     * @param buttonSize the buttonSize to set
     */
    public void setButtonSize(Dimension buttonSize) {
        this.buttonSize = buttonSize;
    }

    /**
     * @return the optButtonSize
     */
    public Dimension getOptButtonSize() {
        return optButtonSize;
    }

    /**
     * @param optButtonSize the optButtonSize to set
     */
    public void setOptButtonSize(Dimension optButtonSize) {
        this.optButtonSize = optButtonSize;
    }

    /**
     * @return the algoRunning
     */
    public boolean isAlgoRunning() {
        return algoRunning;
    }

    /**
     * @param algoRunning the algoRunning to set
     */
    public void setAlgoRunning(boolean algoRunning) {
        this.algoRunning = algoRunning;
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

    public static enum algoType {

        prims, kruskals, dijkstras
    };

    public static enum graphType {

        dw, uw, uu
    }; // Directed - Weighted, Undirected - Weighted, undirected - unweighted

    public static enum nodeType {

        recog, std
    }; // recognised size, or a standadised size

    public static enum inputType {

        numpad, voice, both
    }; // numpad or voice recog

    private void setupKeypad() {
        //Dimension buttonSize = new Dimension(32,32);
        buttons[0] = new Button("7");
        buttons[1] = new Button("8");
        buttons[2] = new Button("9");
        buttons[3] = new Button("del");
        buttons[4] = new Button("4");
        buttons[5] = new Button("5");
        buttons[6] = new Button("6");
        buttons[7] = new Button("clr");
        buttons[8] = new Button("1");
        buttons[9] = new Button("2");
        buttons[10] = new Button("3");
        buttons[11] = new Button("");
        buttons[12] = new Button("0");
        buttons[13] = new Button(".");
        buttons[14] = new Button("ok");
        buttons[15] = new Button("");

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setPreferredSize(getButtonSize());
            buttons[i].setVisible(true);
            add(buttons[i]);
        }
        buttons[7].setPreferredSize(new Dimension(getButtonSize().height, getButtonSize().width * 2));
        buttons[14].setPreferredSize(new Dimension(getButtonSize().height * 2, getButtonSize().width));

        display.setEditable(false);
        display.setBackground(new Color(45, 110, 165));
        display.setForeground(Color.white);
        display.setPreferredSize(new Dimension(75, 25));
        display.setVisible(true);
        add(display);

        // 7 key
        buttons[0].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 7;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 8 key
        buttons[1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 8;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 9 key
        buttons[2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 9;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 4 key
        buttons[4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 4;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 5 key
        buttons[5].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 5;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 6 key
        buttons[6].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 6;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // clear key
        buttons[7].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                display.setText("0");
                firstNum = true;
            }
        });

        // 1 key
        buttons[8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 1;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 2 key
        buttons[9].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 2;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 3 key
        buttons[10].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 3;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    } else {
                        display.setText("" + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // 0 key
        buttons[12].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int i = 0;
                if (!firstNum) {
                    if (!display.getText().equals("0")) {
                        display.setText(display.getText() + i);
                    }
                } else {
                    display.setText("" + i);
                    firstNum = false;
                }
            }
        });

        // . key
        buttons[13].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!firstNum) {
                    if (!display.getText().contains(".")) {
                        display.setText(display.getText() + ".");
                    }// if the previous char was a . then the user shouldn't be able to add another
                } else {
                    display.setText("0.");
                    firstNum = false;
                }
            }
        });

        // delete key
        buttons[3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (display.getText().length() <= 1) {
                    display.setText("0");
                    firstNum = true;
                } else {
                    display.setText(display.getText().substring(0, display.getText().length() - 1));
                }
            }
        });

        // ok key -- ends the keypad
        buttons[14].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (workingEdge != null) {
                    double weight = Double.parseDouble(display.getText());
                    if (weight > 0) {
                        workingEdge.resetWeight(Double.parseDouble(display.getText()), graphics);
                        update(graphics);
                        numpadActive = false;
                        hideNumPad();
                        display.setText("0");
                        firstNum = true;
                        workingEdge = null;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Only Non-Zero & Non-Negative Values Allowed!", "Edge Weighting Error", JOptionPane.ERROR_MESSAGE);
                        display.setText("0");
                    }
                }
                coordinates.clear();
            }
        });
        // 11 and 15 aren't used so are left completely empty
    }

    private void setupNodeOptions() {
        editButtons[0] = new Button("Set as Root");
        editButtons[1] = new Button("Set as End");

        for (int i = 0; i < editButtons.length; i++) {
            editButtons[i].setPreferredSize(new Dimension(80, 32));
            editButtons[i].setVisible(true);
            add(editButtons[i]);
        }

        // set as Root Node
        editButtons[0].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (currentGraph.rootNode != null) {
                    currentGraph.rootNode.resetNode();
                }

                currentGraph.rootNode = selectedNode;

                if (currentGraph.rootNode != previousNode) {
                    previousNode = currentGraph.rootNode;
                    currentGraph.rootNode.setColour(Color.black);
                    currentGraph.rootNode.setasStart();
                } else if (currentGraph.rootNode == previousNode) {
                    if (previousNode == currentGraph.endNode) {
                        currentGraph.endNode.resetNode();
                        currentGraph.endNode = null;
                        currentGraph.rootNode.setColour(Color.black);
                        currentGraph.rootNode.setasStart();
                    } else {
                        currentGraph.rootNode.setColour(Color.black);
                        currentGraph.rootNode.setasStart();
                    }
                }

                repaint();
                hideNodeOptions();
            }
        });

        // set as Goal Node
        editButtons[1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (currentGraph.endNode != null) {
                    currentGraph.endNode.resetNode();
                }

                currentGraph.endNode = selectedNode;

                if (currentGraph.endNode != previousNode) {
                    previousNode = currentGraph.endNode;
                    currentGraph.endNode.setColour(Color.black);
                    currentGraph.endNode.setasEnd();
                } else if (currentGraph.endNode == previousNode) {
                    if (previousNode == currentGraph.rootNode) {
                        currentGraph.rootNode.resetNode();
                        currentGraph.rootNode = null;
                        currentGraph.endNode.setColour(Color.black);
                        currentGraph.endNode.setasEnd();
                    } else {
                        currentGraph.endNode.setColour(Color.black);
                        currentGraph.endNode.setasEnd();
                    }
                }

                repaint();
                hideNodeOptions();
            }
        });
    }

    public final void algoFinished() {
        btn_RUN.setEnabled(true);
        btn_RUN.setVisible(true);
        hinder.setLocation(-100, -100);
        hinder.setVisible(false);
        setAlgoRunning(false);
    }

    public final void algoRunning() {
        repaintBlack();
        btn_RUN.setEnabled(false);
        btn_RUN.setVisible(false);
        hinder.setPreferredSize(btn_RUN.getPreferredSize());
        hinder.setLocation(btn_RUN.getLocation());
        hinder.setVisible(true);
        setAlgoRunning(true);
    }

    private void changesMade() {
        changesMade = true;
        frame.setTitle("*" + currentGraph.getGraphName() + " -- AlgoSim v1.2 -- Main Window");
    }

    @SuppressWarnings("LeakingThisInConstructor")
    public GUI(Graph loadedGraph, final graphIO io, final Image img) {
        this.io = io;
        this.currentGraph.update(loadedGraph);
        if (currentGraph.endNode != null) {
            currentGraph.endNode.setasEnd();
        }
        if (currentGraph.rootNode != null) {
            currentGraph.rootNode.setasStart();
        }
        this.img = img;
        chosenAlgo = currentGraph.Algorithm;
        graph = currentGraph.GraphType;
        node = currentGraph.NodeType;
        input = currentGraph.InputType;
        int statHeight = 175;
        switch (input) {
            case numpad:
                setupKeypad();
                break;
            case voice:
                voice = new speech("speech.config.xml");
                break;
        }
        setupNodeOptions();

        switch (graph) {
            case dw:
                AlgorithmBase.setDirected(true);
                break;
            default:
                AlgorithmBase.setDirected(false);
                break;
        }

        graphics = this.getGraphics();
        statusBar.setPreferredSize(new Dimension(getScreenSize().width, statHeight));
        statusBar.setLayout(new FlowLayout(FlowLayout.TRAILING));
        statusBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        statusBar.setBackground(Color.gray);

        EventHandler handler = new EventHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(getScreenSize().width, getScreenSize().height - statHeight));

        Dimension size = new Dimension(64, 36);

        JButton btn_EXIT = new JButton();
        btn_EXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/exit.png")));
        btn_EXIT.setPreferredSize(size);
        btn_EXIT.setToolTipText("Click to return to the options menu");
        btn_EXIT.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JButton btn_HELP = new JButton();
        btn_HELP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/help.png")));
        btn_HELP.setPreferredSize(size);
        btn_HELP.setToolTipText("Click to open the Help Window");
        btn_HELP.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpPanel(img);
            }
        });


        final JButton btn_LISTEN = new JButton();
        btn_LISTEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/listening.png")));
        btn_LISTEN.setPreferredSize(size);
        //btn_LISTEN.setBorder(BorderFactory.createEtchedBorder());
        //btn_LISTEN.setBorderPainted(false);
        btn_LISTEN.setToolTipText("Click for graph speech commands");
        btn_LISTEN.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (currentGraph.getNodes().size() < 10) {
                    voice.underten = true;
                }
                if (listening) {
                    listening = false;
                    btn_LISTEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/listening.png")));
                } else {
                    listening = true;
                    btn_LISTEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/Islistening.png")));
                    JOptionPane.showMessageDialog(null, "Press Ok, then speak",
                            "Voice Recog... Edge Weights",
                            JOptionPane.INFORMATION_MESSAGE);
                    String command =
                            voice.run();
                    if (command.contains("delete") && voice.currentresult < currentGraph.getNodes().size()) {
                        GraphNode gn = currentGraph.getNodes().get(voice.currentresult);
                        LinkedList<Edge> elist = gn.getEdges();
                        Collection<Edge> eList = new LinkedList<Edge>(elist);
                        for (Edge ed : eList) {
                            GraphNode n = ed.getAdjoiningNode(gn);
                            gn.dereferenceEdge(ed);
                            n.dereferenceEdge(ed);
                            currentGraph.getEdges().remove(ed);
                        }

                        currentGraph.getNodes().remove(voice.currentresult);
                        int i = 0;
                        for (GraphNode n : currentGraph.getNodes()) {
                            n.setName(nodeName + i);
                            i++;
                        }
                        nodeNameIter = currentGraph.getNodes().size();
                        numNodes.setText(nodeText + (currentGraph.getNodes().size()));
                        inkLine = new Path2D.Double();
                        repaint();
                    } else if (command.contains("select") && voice.currentresult < currentGraph.getNodes().size()) {

                        editMode2 = true;
                        editMode = true;
                        recSelected = true;
                        selectedNode = currentGraph.getNodes().get(voice.currentresult);
                        selectedNode.setColour(Color.blue);
                        updateGraphics();
                        repaint();

                    } else if (command.equals("run")) {

                        if (!currentGraph.getNodes().isEmpty() && !currentGraph.getEdges().isEmpty()) {
                            //check for "floating" Nodes
                            int numFNodes = 0;
                            for (GraphNode gn : currentGraph.getNodes()) {
                                if (gn.getEdges().size() < 1) {
                                    numFNodes++;
                                }
                            }

                            if (numFNodes > 0) {
                                JOptionPane.showOptionDialog(null, "You have " + numFNodes + " unconnected Nodes, you cannot continue!", "WARNING: " + numFNodes + " Unconnected Nodes Found!", JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 1);
                            } else {
                                algoRunning();
                                switch (chosenAlgo) {
                                    case dijkstras:
                                        if (currentGraph.rootNode != null && currentGraph.endNode != null) {
                                            DsAlgorithm algoD = new DsAlgorithm(currentGraph, graphics, STDout, PQout, gui);
                                            algoD.run();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Root or Goal nodes not set\n Please set these nodes", "ERROR: Nodes Not Set Error", JOptionPane.WARNING_MESSAGE);
                                            algoFinished();
                                        }
                                        break;
                                    case kruskals:
                                        KruskalsAlgorithm algoK = new KruskalsAlgorithm(currentGraph, graphics, PQout, PQout, gui);
                                        algoK.run();
                                        break;
                                    case prims:
                                        PrimsAlgorithm algoP = new PrimsAlgorithm(currentGraph, graphics, STDout, PQout, gui);
                                        algoP.run();
                                        break;
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "No Computable Graph Found", "ERROR: Incomplete Graph", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                    voice.underten = false;
                    listening = false;
                    btn_LISTEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/listening.png")));
                }

            }
        });

        btn_RUN = new JButton();
        btn_RUN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/run_simple.png")));
        btn_RUN.setPreferredSize(size);
        btn_RUN.setToolTipText("Click to run your chosen algorithm");
        btn_RUN.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                algoRunning();
                if (!currentGraph.getNodes().isEmpty() && !currentGraph.getEdges().isEmpty()) {
                    //check for "floating" Nodes
                    int numFNodes = 0;
                    for (GraphNode gn : currentGraph.getNodes()) {
                        if (gn.getEdges().size() < 1) {
                            numFNodes++;
                        }
                    }
                    if (numFNodes > 0) {
                        JOptionPane.showOptionDialog(null, "You have " + numFNodes + " unconnected Nodes, you cannot continue!", "WARNING: " + numFNodes + " Unconnected Nodes Found!", JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, 1);
                    } else {
                        if (changesMade) {
                            io.saveGraph(currentGraph);
                            changesMade = false;
                            frame.setTitle(currentGraph.getGraphName() + " -- AlgoSim v1.2 -- Main Window");
                        }
                        //btns.setVisible(false);
                        //help.setVisible(false);
                        switch (chosenAlgo) {
                            case dijkstras:
                                if (currentGraph.rootNode != null && currentGraph.endNode != null) {
                                    DsAlgorithm algoD = new DsAlgorithm(currentGraph, graphics, STDout, PQout, gui);
                                    algoD.run();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Root or Goal nodes not set\n Please set these nodes", "ERROR: Nodes Not Set Error", JOptionPane.WARNING_MESSAGE);
                                    algoFinished();
                                }
                                break;
                            case kruskals:
                                KruskalsAlgorithm algoK = new KruskalsAlgorithm(currentGraph, graphics, PQout, PQout, gui);
                                algoK.run();
                                break;
                            case prims:
                                PrimsAlgorithm algoP = new PrimsAlgorithm(currentGraph, graphics, STDout, PQout, gui);
                                algoP.run();
                                break;
                        }
                    }
                    //btn_RUN.setEnabled(true);
                    //btn_RUN.setVisible(true);
                    //btns.setVisible(true);
                    algoFinished();
                } else {
                    JOptionPane.showMessageDialog(frame, "No Computable Graph Found", "ERROR: Incomplete Graph", JOptionPane.ERROR_MESSAGE);
                    algoFinished();
                }
            }
        });

        statusBar.setLayout(new BorderLayout(2, 2));
        STDout.setPreferredSize(new Dimension((getScreenSize().width / 2) - 4, 138));
        STDout.setVisible(true);
        STDout.setEditable(false);
        STDout.setBackground(Color.white);
        PQout.setPreferredSize(new Dimension((getScreenSize().width / 5) - 4, 138));
        PQout.setVisible(true);
        PQout.setEditable(false);
        PQout.setBackground(Color.white);
        //help.setVisible(true);

        /*
         * JPanel centerPanel = new JPanel(); centerPanel.add(help,
         * BorderLayout.SOUTH); centerPanel.add(STDout, BorderLayout.NORTH);
         * centerPanel.setVisible(true);
         */
        statusBar.add(STDout, BorderLayout.CENTER);
        statusBar.add(PQout, BorderLayout.WEST);

        JPanel options = new JPanel();
        options.setLayout(new FlowLayout(FlowLayout.RIGHT));
        options.setPreferredSize(new Dimension((getScreenSize().width / 8) - 4, 138));
        /*
         * JPanel info = new JPanel(); info.setLayout(new
         * FlowLayout(FlowLayout.RIGHT)); numNodes.setText(nodeText +
         * num_Nodes); info.add(numNodes); options.add(info,
         * BorderLayout.SOUTH);
         */
        btn_HELP.setVisible(true);
        btn_RUN.setVisible(true);
        btn_EXIT.setVisible(true);
        btn_LISTEN.setVisible(true);
        btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.setPreferredSize(new Dimension(size.width + 10, 138));
        btns.add(btn_HELP);
        btns.add(btn_RUN);
        if (input == inputType.voice) {
            btn_LISTEN.setEnabled(true);
        } else {
            btn_LISTEN.setEnabled(false);
        }
        btns.add(btn_LISTEN);
        btns.add(btn_EXIT);
        btns.setBackground(statusBar.getBackground());
        //options.add(btns, BorderLayout.NORTH);
        statusBar.add(btns, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(statusBar, BorderLayout.CENTER);
        frame.getContentPane().add(this, BorderLayout.PAGE_START);
        frame.validate();
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocation(0, 0);
        frame.pack();
        frame.setTitle(currentGraph.getGraphName() + " -- AlgoSim v1.2 -- Main Window");
        frame.setVisible(true);

        hideNumPad();
        hideNodeOptions();

        PQout.setVisible(false);
        STDout.setVisible(false);

        frame.setIconImage(this.img);
        new HelpPanel(this.getImg());

        updateGraphics();
        repaint();
    }

    public void repaintBlack() {
        for (GraphNode ns : currentGraph.getNodes()) {
            ns.setColour(Color.black);
        }

        for (Edge es : currentGraph.getEdges()) {
            if (graph == graph.dw) {
                es.setDColour(Color.black);
            }
            es.setColour(Color.black);
        }
    }

    @Override
    public void paintComponent(Graphics ink) {
        super.paintComponent(ink);

        Graphics2D inkLining = (Graphics2D) ink;

        if (start != null && end != null && !editMode2) {
            BasicStroke stroke = new BasicStroke(1);
            Shape strokedShape = stroke.createStrokedShape(inkLine);
            inkLining.draw(strokedShape);
            inkLining.fill(strokedShape);
        }

        for (Edge e : currentGraph.getEdges()) {
            switch (graph) {
                case uw:
                    e.draw_Edge(ink);
                    break;
                case dw:
                    e.draw_DEdge(ink);
                    break;
                default:
                    break;
            }
        }

        for (GraphNode gn : currentGraph.getNodes()) {
            gn.drawNode(ink);
        }

        if (selection != null) {
            selection.drawSelection(ink);
        }
    }

    class EventHandler implements MouseListener, MouseMotionListener {

        private final int MIN_X_VALUE, MIN_Y_VALUE;
        private final int MAX_X_VALUE, MAX_Y_VALUE;
        private boolean mouse_in_canvas;
        private int maxX;
        private int maxY;
        private int minX;
        private int minY;
        //  private int preX = 1, preY = 1;
        private Point inkstart;
        private Point inkend;
        private Shape rect;
        private Shape line;
        private double stdDiameter = 72;
        private GraphNode nodeSelected;
        private LinkedList<GraphNode> selectedNodes = new LinkedList<GraphNode>();
        private boolean edge_editMode2;
        private boolean edge_editMode;
        private GraphNode startNode = null;
        private GraphNode endNode = null;
        private boolean node_Selected = false;
        private boolean edge_Selected = false;
        private int[] selectionPreX, selectionPreY;

        /*
         * - when setting a node a root node, check currentGraph.getNodes() for
         * the node with the same name and assign rootNode to reference it -
         * same for goal node - when deleting a node, check if it is the root
         * node and set the new root as first? node in currentGraph.getNodes()
         * (if currentGraph.getNodes() not empty and first is not the root)
         */
        public EventHandler() {
            MIN_X_VALUE = 0;
            MIN_Y_VALUE = 0;
            MAX_X_VALUE = getScreenSize().width;
            MAX_Y_VALUE = getScreenSize().height;
        }

        private void resetValues() {
            this.minX = MAX_X_VALUE;
            this.minY = MAX_Y_VALUE;
            this.maxX = MIN_X_VALUE;
            this.maxY = MIN_Y_VALUE;
        }

        public void mouseClicked(MouseEvent event) {

            if (!numpadActive && !nodeEditActive && !isAlgoRunning() && ((chosenAlgo.equals(algoType.dijkstras) || currentGraph.advancedMode))) {
                for (GraphNode gnode : currentGraph.getNodes()) {
                    if (gnode.contains(event.getLocationOnScreen())) {
                        selectedNode = gnode;
                        showNodeOptions(); // as this will operate on the selected node, there is no need to pass it anything
                        inkLine = new Path2D.Double();
                        coordinates.clear();
                        repaint();
                        changesMade();
                        return;
                    }
                }
            }
            currentGraph.updateGraph(currentGraph.getNodes(), currentGraph.getEdges());
        }

        public void mousePressed(MouseEvent event) {

            this.mouse_in_canvas = true;
            resetValues(); // initialise values

            start = event.getPoint();
            inkstart = event.getPoint();
            inkend = event.getPoint();

            Path2D path = new Path2D.Double();
            inkLine = path;

            if (editMode2) {
                for (GraphNode gn : currentGraph.getNodes()) {
                    if ((rect.contains(event.getPoint()) && rect.contains(gn.getBounds2D())) || selection.contains(event.getPoint())) {
                        editMode2 = true;
                        editMode = true;
                        nodeSelected = gn;
                        nodeSelected.setColour(Color.blue);
                        updateGraphics();
                        repaint();

                        // node move
                        // (rect.x = x coord) - (x coord of mouse pointer)
                        nodeSelected.setpreX((nodeSelected.getX() - event.getX()));
                        // (rect.y = y coord) - (y coord of mouse pointer)
                        nodeSelected.setpreY((nodeSelected.getY() - event.getY()));
                        selectedNodes.add(nodeSelected);

                        int[] xcoords = selection.getX();

                        selectionPreX = new int[selection.getX().length];
                        selectionPreY = new int[selection.getY().length];

                        for (int i = 0; i < selection.getX().length; i++) {
                            selectionPreX[i] = (xcoords[i] - event.getX());
                        }

                        int[] ycoords = selection.getY();

                        for (int i = 0; i < selection.getY().length; i++) {
                            selectionPreY[i] = (ycoords[i] - event.getY());
                        }

                    } else if (recSelected) {

                        editMode2 = true;
                        editMode = true;
                        nodeSelected = selectedNode;
                        nodeSelected.setColour(Color.blue);
                        updateGraphics();
                        repaint();

                        // node move
                        // (rect.x = x coord) - (x coord of mouse pointer)
                        nodeSelected.setpreX((nodeSelected.getX() - event.getX()));
                        // (rect.y = y coord) - (y coord of mouse pointer)
                        nodeSelected.setpreY((nodeSelected.getY() - event.getY()));

                    } else if (inkend.equals(inkstart) && !rect.contains(event.getPoint())) {
                        editMode2 = false;
                        editMode = false;
                        inkLine = new Path2D.Double();
                        repaint();
                    }
                }
            } else if (numpadActive) {
                if (!calRect.contains(event.getPoint())) {
                    GraphNode[] edgeNodes = workingEdge.getNodes();
                    edgeNodes[0].dereferenceEdge(workingEdge);
                    edgeNodes[1].dereferenceEdge(workingEdge);
                    currentGraph.getEdges().remove(workingEdge);
                    hideNumPad();
                }
            } else if (nodeEditActive) {
                if (nodeOptRect.contains(event.getPoint())) {
                    hideNodeOptions();
                    repaint();
                }
            }
            changesMade();
            currentGraph.updateGraph(currentGraph.getNodes(), currentGraph.getEdges());
        }

        public void mouseDragged(MouseEvent event) {
            if (!numpadActive && !nodeEditActive && !isAlgoRunning()) {
                end = event.getPoint();

                Path2D path = (Path2D) inkLine;
                path.moveTo(start.x, start.y);
                path.lineTo(end.x, end.y);
                inkLine = path;
                start = end;
                repaint();

                this.maxX = Math.max(this.maxX, Math.max(start.x, end.x));
                this.minX = Math.min(this.minX, Math.min(start.x, end.x));
                this.maxY = Math.max(this.maxY, Math.max(start.y, end.y));
                this.minY = Math.min(this.minY, Math.min(start.y, end.y));

                if (!editMode2) {
                    coordinates.add(event.getPoint());
                }

                if (mouse_in_canvas && !numpadActive & !nodeEditActive) {
                    if ((start.x < 0 || start.y < 0 || end.x < 0 || end.y < 0)) {
                        this.mouse_in_canvas = false;
                    } else if ((start.x > getScreenSize().width || end.x > getScreenSize().width || start.y > getScreenSize().height - 175 || end.y > getScreenSize().height - 175)) {
                        this.mouse_in_canvas = false;
                    }

                    if (editMode2) {
                        redraw(graphics, event);
                        editMode = false;
                        if (selectedNode != null) {
                            selectedNode.setColour(Color.blue);
                        }
                    }
                }
                inkend = event.getPoint();
            }
            changesMade();
            currentGraph.updateGraph(currentGraph.getNodes(), currentGraph.getEdges());
        }

        public void redraw(Graphics g, MouseEvent e) {
            if (recSelected) {
                selectedNode.setX(nodeSelected.getpreX() + e.getX());
                selectedNode.setY(nodeSelected.getpreY() + e.getY());
                repaint();
            } else {
                boolean noCollision = true;
                for (GraphNode gn : selectedNodes) {
                    if (gn.getBounds2D().getMinX() >= 5 && gn.getBounds2D().getMinY() >= 5
                            && gn.getBounds2D().getMaxX() <= getScreenSize().width - 5 && gn.getBounds2D().getMaxY() <= getScreenSize().height - 180) {
                        /*
                         * gn.setX(gn.getpreX() + e.getX());
                         * gn.setY(gn.getpreY() + e.getY());
                         */
//                        for (GraphNode others : currentGraph.getNodes()) {
//                            if (others != gn) {
//                                if (others.getBounds2D().contains(new Point((int) gn.getBounds2D().getMinX(), (int) gn.getBounds2D().getMinY()))
//                                        || others.getBounds2D().contains(new Point((int) gn.getBounds2D().getMinX(), (int) gn.getBounds2D().getMaxY()))
//                                        || others.getBounds2D().contains(new Point((int) gn.getBounds2D().getMaxX(), (int) gn.getBounds2D().getMinY()))
//                                        || others.getBounds2D().contains(new Point((int) gn.getBounds2D().getMaxX(), (int) gn.getBounds2D().getMaxY()))) {
//                                    gn.setColour(Color.RED);
//                                    noCollision = false;
//                                } else {
//                                    gn.resetNode();
//                                    gn.lastSafeX = gn.getX();
//                                    gn.lastSafeY = gn.getY();
//                                }
//                            }
//                        }
                        if (noCollision) {
                            gn.setX(gn.getpreX() + e.getX());
                            gn.setY(gn.getpreY() + e.getY());
                        } else {
                            gn.setX(gn.lastSafeX);
                            gn.setY(gn.lastSafeY);
                        }
                    } else {
                        if (gn.getBounds2D().getMinX() <= 5) {
                            gn.setX(6);
                            gn.setY(gn.getY());
                        }
                        if (gn.getBounds2D().getMinY() <= 5) {
                            gn.setX(gn.getX());
                            gn.setY(6);
                        }
                        if (gn.getBounds2D().getMaxX() >= getScreenSize().width - 5) {
                            gn.setX((getScreenSize().width - 6) - gn.getWidth());
                            gn.setY(gn.getY());
                        }
                        if (gn.getBounds2D().getMaxY() >= getScreenSize().height - 180) {
                            gn.setX(gn.getX());
                            gn.setY((getScreenSize().height - 181) - gn.getHeight());
                        }
                    }
                }
                repaint();
            }

            int[] xcoords, ycoords;
            xcoords = new int[selection.getX().length];
            ycoords = new int[selection.getY().length];

            for (int i = 0; i < selection.getX().length; i++) {
                xcoords[i] = (selectionPreX[i] + e.getX());
            }

            for (int i = 0; i < selection.getY().length; i++) {
                ycoords[i] = (selectionPreY[i] + e.getY());
            }

            selection.setX(xcoords);
            selection.setY(ycoords);

            repaint();
            currentGraph.updateGraph(currentGraph.getNodes(), currentGraph.getEdges());
        }

        public void mouseReleased(MouseEvent event) {
            int iee = 0;
            for (GraphNode gn : currentGraph.getNodes()) {
                if (!gn.isNamed) {
                    gn.setName(nodeName + iee);
                    iee++;
                }
            }
            nodeNameIter = currentGraph.getNodes().size();
            if (!numpadActive && !nodeEditActive && !isAlgoRunning()) {
                if (inkend == null) {
                    inkend = inkstart;
                }

                Path2D path = (Path2D) inkLine;
                try {
                    path.closePath();
                } catch (Exception ingore) {
                }

                inkLine = path;

                rect = null;
                isCircle();
                startNode = null;
                endNode = null;
                updateGraphics();
                repaint();

                if (mouse_in_canvas) {
                    if (editMode2) {
                        if (!coordinates.isEmpty()) {
                            selection = new Selection(coordinates);
                            repaint();
                        }
                    } else if (!inkend.equals(inkstart) && !editMode2 && !edge_editMode2) {
                        repaintBlack();
                        if (isCircle()) { // ### Node Drawing ###
                            double diameter = 0;
                            switch (node) {
                                case recog:
                                    int px,
                                     py;
                                    px = (maxX - minX);
                                    py = (maxY - minY);
                                    diameter = (px + py) / 2;
                                    if(diameter > maxSize){
                                        diameter = maxSize;
                                    }
                                    break;
                                case std:
                                    diameter = stdDiameter;
                            }

                            GraphNode gn = new GraphNode(nodeName + (nodeNameIter++), this.minX, this.minY, (int) diameter);

                            for (GraphNode gnode : currentGraph.getNodes()) {
                                if (gnode.contains(new Point(this.minX, this.minY)) || gnode.contains(new Point(this.minX, this.maxY)) || gnode.contains(new Point(this.maxX, this.minY)) || gnode.contains(new Point(this.maxX, this.maxY)) || gn.contains(new Point(gnode.getX(), gnode.getY())) || gn.contains(new Point(gnode.getWidth(), gnode.getHeight())) || gn.contains(new Point((int) gnode.getBounds2D().getMaxX(), (int) gnode.getBounds2D().getMinY())) || gn.contains(new Point((int) gnode.getBounds2D().getMaxY(), (int) gnode.getBounds2D().getMinX())) || gn.contains(new Point((int) gnode.getBounds2D().getMaxX(), (int) gnode.getBounds2D().getMaxY()))) {
                                    JOptionPane.showMessageDialog(null, "Cannot Create Node Here: To close to a Neighbour", "Node Creation Error", JOptionPane.INFORMATION_MESSAGE);
                                    inkLine = new Path2D.Double();
                                    repaint();
                                    changesMade();
                                    coordinates.clear();
                                    return;
                                }
                            }

                            if (currentGraph.rootNode == null) {
                                currentGraph.rootNode = gn;
                            }

                            currentGraph.getNodes().add(gn);
                            numNodes.setText(nodeText + (currentGraph.getNodes().size()));
                            inkLine = new Path2D.Double();
                            coordinates.clear();
                            repaint();
                            changesMade();
                            return;
                        } else { // ### Edge Drawing ###
                            isCircle();
                            String evalue = null;

                            while (true) {
                                try {
                                    if (!currentGraph.getEdges().isEmpty()) {
                                        Collection<Edge> edges_ = new LinkedList<Edge>(currentGraph.getEdges());
                                        for (Edge edge : edges_) {
                                            if (startNode == null || endNode == null) {
                                                if (boundingBox() < 4) {
                                                    mouseClicked(event);
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Edge must connect two different nodes", "Edge Error 2", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                                inkLine = new Path2D.Double();
                                                repaint();
                                                startNode = null;
                                                endNode = null;
                                                coordinates.clear();
                                                changesMade();
                                                return;
                                            } else if (edge.getAdjoiningNode(startNode).equals(endNode) && edge.getAdjoiningNode(endNode).equals(startNode)) {
                                                JOptionPane.showMessageDialog(null, "Edge must not connect two nodes already connected", "Edge Error", JOptionPane.INFORMATION_MESSAGE);
                                                inkLine = new Path2D.Double();
                                                repaint();
                                                startNode = null;
                                                endNode = null;
                                                coordinates.clear();
                                                changesMade();
                                                return;
                                            } else {
                                                //this is where the main change was made
                                                int x1 = startNode.getCentreX(), y1 = startNode.getCentreY(), x2 = endNode.getCentreX(), y2 = endNode.getCentreY();
                                                double eWeight = 0.0;
                                                Edge nEdge = null;
                                                switch (input) {
                                                    case numpad:
                                                        nEdge = new Edge(eWeight, startNode, endNode);
                                                        showNumpad(nEdge);
                                                        inkLine = new Path2D.Double();
                                                        break;
                                                    case voice:
                                                        if (currentGraph.getNodes().size() < 10) {
                                                            voice.underten = true;
                                                        }
                                                        voice.setDigit(true);
                                                        JOptionPane.showMessageDialog(null, "Press Ok, then speak", "Voice Recog... Edge Weights", JOptionPane.INFORMATION_MESSAGE);
                                                        String weight = voice.run();
                                                        eWeight = Double.parseDouble(weight);
                                                        nEdge = new Edge(eWeight, startNode, endNode);
                                                        inkLine = new Path2D.Double();
                                                        startNode = null;
                                                        endNode = null;
                                                        voice.setDigit(false);
                                                        voice.underten = false;
                                                        break;
                                                    default:
                                                        break;
                                                }
                                                currentGraph.getEdges().add(nEdge);
                                                inkLine = new Path2D.Double();
                                                repaint();
                                                startNode = null;
                                                endNode = null;
                                                coordinates.clear();
                                                changesMade();
                                                return;
                                            }
                                        }
                                    } else {
                                        if (endNode == null || startNode == null) {
                                            if (boundingBox() < 4) {
                                                mouseClicked(event);
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Edge must connect two different nodes", "Edge Error 1", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                            inkLine = new Path2D.Double();
                                            repaint();
                                            startNode = null;
                                            endNode = null;
                                            coordinates.clear();
                                            changesMade();
                                            return;
                                        } else {
                                            //this is where the main change was made
                                            //int x1 = startNode.getCentreX(), y1 = startNode.getCentreY(), x2 = endNode.getCentreX(), y2 = endNode.getCentreY();
                                            double eWeight = 0.0;
                                            Edge nEdge = null;
                                            switch (input) {
                                                case numpad:
                                                    nEdge = new Edge(eWeight, startNode, endNode);
                                                    showNumpad(nEdge);
                                                    inkLine = new Path2D.Double();
                                                    startNode = null;
                                                    endNode = null;
                                                    break;
                                                case voice:
                                                    if (currentGraph.getNodes().size() < 10) {
                                                        voice.underten = true;
                                                    }
                                                    voice.setDigit(true);
                                                    JOptionPane.showMessageDialog(null, "Press Ok, then speak", "Voice Recog... Edge Weights", JOptionPane.INFORMATION_MESSAGE);
                                                    String weight = voice.run();
                                                    eWeight = Double.parseDouble(weight);
                                                    nEdge = new Edge(eWeight, startNode, endNode);
                                                    inkLine = new Path2D.Double();
                                                    startNode = null;
                                                    endNode = null;
                                                    voice.setDigit(false);
                                                    voice.underten = false;
                                                    break;
                                                default:
                                                    break;
                                            }
                                            currentGraph.getEdges().add(nEdge);
                                            inkLine = new Path2D.Double();
                                            repaint();
                                            startNode = null;
                                            endNode = null;
                                            coordinates.clear();
                                            changesMade();
                                            return;
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(null, "Invalid Length " + evalue);
                                    startNode = null;
                                    endNode = null;
                                    break;
                                }
                            }
                            inkLine = new Path2D.Double();
                            coordinates.clear();
                            repaint();
                        }
                        if (!editMode) {
                            editMode2 = false;
                        } else if (!edge_editMode) {
                            edge_editMode2 = false;
                        }
                    } else {
                        if (edge_editMode) {
                            for (Edge edge : currentGraph.getEdges()) {
                                if (rect.contains(edge.getValue())) {
                                    switch (input) {
                                        case numpad:
                                            showNumpad(edge);
                                            break;
                                        case voice:
                                            if (currentGraph.getNodes().size() < 10) {
                                                voice.underten = true;
                                            }
                                            voice.setDigit(true);
                                            String weight = null;
                                            while (weight == null || weight.equals("")) {
                                                JOptionPane.showMessageDialog(null, "Press Ok, then speak", "Voice Recog... Edge Weights", JOptionPane.INFORMATION_MESSAGE);
                                                weight = voice.run();

                                            }
                                            double length = Double.parseDouble(weight);
                                            edge.resetWeight(length, graphics);
                                            voice.setDigit(false);
                                            voice.underten = false;
                                            break;
                                        default:
                                            break;
                                    }
                                    repaint();
                                }
                            }
                        }
                        inkLine = new Path2D.Double();
                    }
                    if (!editMode) {
                        editMode2 = false;
                        if (selection != null) {
                            selection.resetSelection();
                        }
                    } else if (!edge_editMode) {
                        edge_editMode2 = false;
                    }
                    edge_editMode = false;
                    edge_editMode2 = false;
                    node_Selected = false;
                    edge_Selected = false;
                    inkLine = new Path2D.Double();

                    if (selectedNode != null && !editMode) {
                        if (recSelected) {
                            recSelected = false;
                            selectedNode.setColour(Color.black);
                        } else {
                            for (GraphNode gn : selectedNodes) {
                                gn.setColour(Color.black);
                            }
                            selectedNodes.clear();
                        }
                    }
                    inkLine = new Path2D.Double();
                    editMode = false;
                    updateGraphics();
                    repaint();
                } else {
                    inkLine = new Path2D.Double();
                    editMode = false;
                    repaint();
                }
                updateGraphics();
            } else {
                inkLine = new Path2D.Double();
                editMode = false;
                updateGraphics();
                repaint();
            }
            coordinates.clear();
            currentGraph.updateGraph(currentGraph.getNodes(), currentGraph.getEdges());
        }

        // compare bounding box length and start and end length
        public boolean isCircle() {
            if (startAndendLength() < boundingBox()) {
                return true;
            }
            return false;
        }

        // calculate the length from one corner of the bounding box to the other
        public double boundingBox() {
            double xl, yl, length;

            xl = this.maxX - this.minX;
            yl = this.maxY - this.minY;

            length = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

            length *= 100;
            Math.round(length);
            length /= 100;

            double refactoredLength;

            refactoredLength = (5.8 * length) / 10;

            for (GraphNode gn : currentGraph.getNodes()) {
                if (gn.contains(inkstart)) {
                    startNode = gn;
                }
            }

            for (GraphNode gn : currentGraph.getNodes()) {
                if (gn.contains(inkend) && !gn.equals(startNode)) {
                    endNode = gn;
                }
            }

            calRect = new Rectangle2D.Double(loc_calc.x, loc_calc.y, 128, 160);
            nodeOptRect = new Rectangle2D.Double(loc_nodeOpt.x, loc_nodeOpt.y, 128, 160);
            rect = new Rectangle2D.Double(this.minX, this.minY, xl, yl);

            if (inkstart.x < inkend.x) {
                line = new Rectangle2D.Double(this.inkstart.x, this.inkstart.y, (this.inkend.x - this.inkstart.x), (this.inkend.y - this.inkstart.y));
            } else if (inkstart.x > inkend.x) {
                line = new Rectangle2D.Double(this.inkstart.x - (this.inkstart.x - inkend.x), this.inkstart.y, (this.inkstart.x - this.inkend.x), (this.inkend.y - this.inkstart.y));
            }
            //drect = new Rectangle2D.Double(this.minX, this.minY, xl, yl);

            // Selcting nodes
            if (!inkend.equals(inkstart) && startAndendLength() < refactoredLength && !editMode2 && !edge_editMode) {
                for (GraphNode gn : currentGraph.getNodes()) {
                    if (rect.contains(gn.getBounds2D())) {
                        editMode2 = true;
                        editMode = true;
                        selectedNode = gn;
                        node_Selected = true;
                        selectedNode.setColour(Color.blue);
                        updateGraphics();
                        selectedNodes.add(selectedNode);
                        //break;
                        //System.err.println("editMode on! - Contains a node!");
                    }
                }

                for (Edge edge : currentGraph.getEdges()) {
                    if (rect.contains(edge.getValue()) && startNode == null && endNode == null) {
                        edge_editMode2 = true;
                        edge_editMode = true;
                        edge_Selected = true;
                        //System.err.println("edge_editMode on! - Contains an edge!");
                        break;
                    }
                }

                if (node_Selected && edge_Selected) {
                    edge_editMode2 = false;
                    edge_editMode = false;
                }
            }


            // removal of Nodes from this point on
            if (!inkend.equals(inkstart) && startAndendLength() > refactoredLength && !editMode2 && !edge_editMode) {

                Collection<GraphNode> nss = new LinkedList<GraphNode>(selectedNodes);
                for (GraphNode n : nss) {
                    if ((((selection.getMinX() < inkstart.x && selection.getMinY() > inkstart.y) && (selection.getMaxX() > inkend.x && selection.getMaxY() < inkend.y)) && startNode == null && endNode == null && selection != null)
                            || ((((selection.getMinY() < inkstart.y && selection.getMinX() > inkstart.x) && (selection.getMaxX() < inkend.x && selection.getMaxY() > inkend.y))) && startNode == null && endNode == null && selection != null)
                            || (((selection.getMinX() < inkstart.x && selection.getMaxY() < inkstart.y) && (selection.getMaxX() > inkend.x && selection.getMinY() > inkend.y)) && startNode == null && endNode == null && selection != null)
                            || ((((selection.getMinY() < inkstart.y && selection.getMaxX() < inkstart.x) && (selection.getMinX() > inkend.x && selection.getMaxY() > inkend.y))) && startNode == null && endNode == null && selection != null)
                            || ((((selection.getMinY() > inkstart.y && selection.getMinX() > inkstart.x) && (selection.getMaxX() < inkend.x && selection.getMaxY() < inkend.y))) && startNode == null && endNode == null && selection != null)) {

                        currentGraph.getNodes().removeAll(nss);
                        LinkedList<Edge> elist = n.getEdges();
                        Collection<Edge> eList = new LinkedList<Edge>(elist);
                        for (Edge e : eList) {
                            GraphNode gn = e.getAdjoiningNode(n);
                            gn.dereferenceEdge(e);
                            n.dereferenceEdge(e);
                            currentGraph.getEdges().remove(e);
                        }
                    }

                    inkLine = new Path2D.Double();
                    editMode2 = true;
                    editMode = false;
                    edge_editMode2 = true;
                    edge_editMode = false;
                    coordinates.clear();
                    repaint();
                }
                selection = null;

                Collection<GraphNode> gnodes = new LinkedList<GraphNode>(currentGraph.getNodes());
                for (GraphNode gn : gnodes) {
                    if (!inkend.equals(inkstart) && startAndendLength() > refactoredLength && !editMode2 && !edge_editMode) {
                        if ((((gn.getBounds2D().getMinX() < inkstart.x && gn.getBounds2D().getMinY() > inkstart.y) && (gn.getBounds2D().getMaxX() > inkend.x && gn.getBounds2D().getMaxY() < inkend.y)) && startNode == null && endNode == null) || ((((gn.getBounds2D().getMinY() < inkstart.y && gn.getBounds2D().getMinX() > inkstart.x) && (gn.getBounds2D().getMaxX() < inkend.x && gn.getBounds2D().getMaxY() > inkend.y))) && startNode == null && endNode == null) || (((gn.getBounds2D().getMinX() < inkstart.x && gn.getBounds2D().getMaxY() < inkstart.y) && (gn.getBounds2D().getMaxX() > inkend.x && gn.getBounds2D().getMinY() > inkend.y)) && startNode == null && endNode == null) || ((((gn.getBounds2D().getMinY() < inkstart.y && gn.getBounds2D().getMaxX() < inkstart.x) && (gn.getBounds2D().getMinX() > inkend.x && gn.getBounds2D().getMaxY() > inkend.y))) && startNode == null && endNode == null) || ((((gn.getBounds2D().getMinY() > inkstart.y && gn.getBounds2D().getMinX() > inkstart.x) && (gn.getBounds2D().getMaxX() < inkend.x && gn.getBounds2D().getMaxY() < inkend.y))) && startNode == null && endNode == null)) {
                            LinkedList<Edge> elist = gn.getEdges();
                            Collection<Edge> eList = new LinkedList<Edge>(elist);
                            for (Edge e : eList) {
                                GraphNode n = e.getAdjoiningNode(gn);
                                gn.dereferenceEdge(e);
                                n.dereferenceEdge(e);
                                currentGraph.getEdges().remove(e);
                            }
                            currentGraph.getNodes().remove(gn);
                            int i = 0;
                            for (GraphNode n : currentGraph.getNodes()) {
                                n.setName(nodeName + i);
                                i++;
                            }
                            numNodes.setText(nodeText + (--num_Nodes));
                            nodeNameIter = currentGraph.getNodes().size();
                            inkLine = new Path2D.Double();
                            editMode2 = true;
                            editMode = false;
                            edge_editMode2 = true;
                            edge_editMode = false;
                            coordinates.clear();
                            repaint();
                        } else {
                        }
                    }
                }

                // removal of Edges
                Collection<Edge> edgeS = new LinkedList<Edge>(currentGraph.getEdges());
                for (Edge edge : edgeS) {
                    if ((line.contains(edge.getValue()) && startNode == null && endNode == null) || (line.contains(edge.getValue()) && startNode == null && endNode == null) || (line.contains(edge.getValue()) && startNode == null && endNode == null) || (line.contains(edge.getValue()) && startNode == null && endNode == null)) {

                        //System.err.println("Line contains XY!");
                        GraphNode[] nList = edge.getNodes();
                        for (int i = 0; i < nList.length; i++) {
                            nList[i].dereferenceEdge(edge);
                        }
                        currentGraph.getEdges().remove(edge);
                        inkLine = new Path2D.Double();
                        edge_editMode2 = true;
                        edge_editMode = false;
                        editMode2 = true;
                        editMode = false;
                        coordinates.clear();
                        repaint();
                    } else {
                    }
                }
            }
            currentGraph.updateGraph(currentGraph.getNodes(), currentGraph.getEdges());
            return refactoredLength;
        }

        // calculate the length from the start and the end of the stroke
        public double startAndendLength() {
            double xl, yl, length;

            xl = this.inkend.x - this.inkstart.x;
            yl = this.inkend.y - this.inkstart.y;

            length = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

            length *= 100;
            Math.round(length);
            length /= 100;

            return length;
        }

        // when mouse enters the canvas
        public void mouseEntered(MouseEvent event) {
            //statusBar.setText("Mouse in canvas");
        }

        // mouse exits the canvas
        public void mouseExited(MouseEvent event) {
            //statusBar.setText("Mouse not in canvas");
        }

        // mouse moving when not pressed
        public void mouseMoved(MouseEvent event) {
            //statusBar.setText("Mouse moved");
        }
    }

    private void showNodeOptions() {
        nodeEditActive = true;
        if (currentGraph.advancedMode) {
            new nodeOptions((selectedNode), currentGraph, gui);
        } else {
            int locX = selectedNode.getCentreX() - 80;
            int locY = selectedNode.getCentreY() + 10;
            loc_nodeOpt = new Point(locX, locY);
            for (int i = 0; i < editButtons.length; i++) {
                editButtons[i].setLocation(locX, locY);
                locX += 81;
                editButtons[i].setVisible(true);
            }
            selectedNode.setColour(Color.blue);
        }
    }

    public void hideNodeOptions() {
        nodeEditActive = false;
        for (int i = 0; i < editButtons.length; i++) {
            editButtons[i].setVisible(false);
        }
    }

    private void showNumpad(Edge e) {
        if (input == inputType.numpad) {
            //x coordinates: strX + ((endX - strX) >> 1) - (metrics.stringWidth(String.valueOf(weight) + 30000) >> 1),
            //y coordinates: strY + ((endY - strY) >> 1) + 20
            numpadActive = true;
            int numX, numY;
            workingEdge = e;
            int strX = e.getNodes()[0].getCentreX();
            int strY = e.getNodes()[0].getCentreY();
            int endX = e.getNodes()[1].getCentreX();
            int endY = e.getNodes()[1].getCentreY();
            numX = strX + ((endX - strX) >> 1);
            numY = strY + ((endY - strY) >> 1) + 20;
            int incr = getButtonSize().height;
            if (numX + incr * 3 > this.getWidth()) {
                numX = numX - 200;
            }
            if (numX < 100) {
                numX = numX + 100;
            }
            if (numY + incr * 6 > this.getHeight()) {
                numY = numY - 240;
            }
            loc_calc = new Point(numX + incr, numY);
            for (int i = 0; i < buttons.length; i++) {
                if (i % 4 == 0) { // if this is divisible by 3 then move to the next line
                    numX -= incr * 3;
                    numY += incr;
                } else { // else just move to the next location in the line
                    numX += incr;
                }

                buttons[i].setLocation(new Point(numX, numY));
                buttons[i].setVisible(true);
            }
            display.setVisible(true);
            display.setLocation(numX - incr * 3, numY + incr);
        }
    }

    private void hideNumPad() {
        if (input == inputType.numpad) {
            numpadActive = false;
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(false);
            }
            display.setVisible(false);
        }
    }

    private void updateGraphics() {
        graphics = this.getGraphics();

    }
}