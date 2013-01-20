
import GUI.GUI;
import GUI.Graph;
import GUI.graphIO.graphIO;
import java.awt.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Tom
 */
public class algoSim_Start extends javax.swing.JPanel {

    Image img;
    graphIO io;
    private GUI.algoType algoMode = GUI.algoType.prims;
    private GUI.nodeType ndeType = GUI.nodeType.recog;
    private GUI.graphType graphmode = GUI.graphType.uw;
    private GUI.inputType inputmode = GUI.inputType.numpad;

    public algoSim_Start(LayoutManager layout) {
        super(layout);
    }

    public static void main(String args[]) {
        new algoSim_Start();
    }

    /**
     * Creates new form algoSim_Start
     */
    public algoSim_Start() {
        initComponents();

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame();
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Serenity *Beta* (0.1.2)");
        frame.setLocation((screensize.width / 2) - (this.getWidth() / 2), (screensize.height / 2) - (this.getHeight() / 2));
        img = new javax.swing.ImageIcon(getClass().getResource("/GUI/Images/logo.png")).getImage();
        frame.setIconImage(img);
        //frame.setUndecorated(true);
        frame.setVisible(true);
        io = new graphIO(img);
        this.setVisible(true);

        cmb_AlgoType.setBackground(Color.white);
        cmb_GraphType.setBackground(Color.white);
        cmb_InputType.setBackground(Color.white);
        cmb_NodeType.setBackground(Color.white);
        cmb_GraphType.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser_Graph = new javax.swing.JFileChooser();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cmb_AlgoType = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmb_InputType = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmb_GraphType = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cmb_NodeType = new javax.swing.JComboBox();
        btn_Load = new javax.swing.JButton();
        btn_Start = new javax.swing.JButton();
        btn_Reset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        chk_Advanced = new javax.swing.JCheckBox();
        chk_Teaching = new javax.swing.JCheckBox();

        cmb_AlgoType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Prims Algorithm", "Kruskals Algorithm", "Dijkstras Algorithm" }));
        cmb_AlgoType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_AlgoTypeActionPerformed(evt);
            }
        });

        jLabel1.setText("Choose Algorithm:");

        jLabel2.setText("Choose Input Type:");

        cmb_InputType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Numpad Entry", "Voice Recognition" }));
        cmb_InputType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_InputTypeActionPerformed(evt);
            }
        });

        jLabel3.setText("Choose Graph Type:");

        cmb_GraphType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Undirected Weighted Graph", "Directed Weighted Graph" }));
        cmb_GraphType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_GraphTypeActionPerformed(evt);
            }
        });

        jLabel4.setText("Choose Node Size:");

        cmb_NodeType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "As Drawn (or Nearest Approx.)", "Automatic Size (72px)" }));
        cmb_NodeType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_NodeTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb_AlgoType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_InputType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_GraphType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_NodeType, 0, 293, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_AlgoType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_InputType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_GraphType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_NodeType, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Load.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Load.setText("Load");
        btn_Load.setToolTipText("Loads a complete graph from the programs directory into memory");
        btn_Load.setMaximumSize(new java.awt.Dimension(61, 23));
        btn_Load.setPreferredSize(new java.awt.Dimension(61, 23));
        btn_Load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LoadActionPerformed(evt);
            }
        });

        btn_Start.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_Start.setText("Start");
        btn_Start.setToolTipText("Run the Program with the current options");
        btn_Start.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_Start.setDoubleBuffered(true);
        btn_Start.setPreferredSize(new java.awt.Dimension(61, 23));
        btn_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_StartActionPerformed(evt);
            }
        });

        btn_Reset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_Reset.setText("Reset");
        btn_Reset.setToolTipText("Reset the Input to the Default Options");
        btn_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ResetActionPerformed(evt);
            }
        });

        chk_Advanced.setText("Advanced Mode");
        chk_Advanced.setToolTipText("Enables Advanced Mode, allowing you to view more indepth information about Nodes and Edges, and also enables the menu bar to allow loading/saving of graphs during the running program");
        chk_Advanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_AdvancedActionPerformed(evt);
            }
        });

        chk_Teaching.setText("Teaching Mode");
        chk_Teaching.setToolTipText("Enables Teaching Mode Options, allow you to interact more with the simulation process");
        chk_Teaching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chk_TeachingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chk_Teaching)
                    .addComponent(chk_Advanced))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chk_Teaching)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk_Advanced)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_Load, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)))
                        .addComponent(btn_Start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Load, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_Reset, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_Start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Start
    private void btn_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_StartActionPerformed
        // TODO add your handling code here:
        Graph graph = new Graph();
        graph.Algorithm = algoMode;
        graph.GraphType = graphmode;
        graph.InputType = inputmode;
        graph.NodeType = ndeType;
        graph.advancedMode = chk_Advanced.isSelected();
        graph.teachingMode = chk_Teaching.isSelected();
        new GUI(graph, new graphIO(img), img);
    }//GEN-LAST:event_btn_StartActionPerformed

    // Reset
    private void btn_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ResetActionPerformed
        // TODO add your handling code here:
        cmb_AlgoType.setSelectedIndex(0);
        cmb_InputType.setSelectedIndex(0);
        cmb_GraphType.setSelectedIndex(0);
        cmb_NodeType.setSelectedIndex(0);
        chk_Advanced.setSelected(false);
        chk_Teaching.setSelected(false);
    }//GEN-LAST:event_btn_ResetActionPerformed

    // Load
    private void btn_LoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LoadActionPerformed
        // TODO add your handling code here
        fileChooser_Graph = new JFileChooser("./save/");
        fileChooser_Graph.showOpenDialog(this);
        fileChooser_Graph.setVisible(true);
        io.loadGraph("save/"+fileChooser_Graph.getName(fileChooser_Graph.getSelectedFile()));
    }//GEN-LAST:event_btn_LoadActionPerformed

    private void cmb_NodeTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_NodeTypeActionPerformed
        // TODO add your handling code here:
        int i = cmb_NodeType.getSelectedIndex();
        switch (i) {
            case 0:
                ndeType = GUI.nodeType.std;
                break;
            case 1:
                ndeType = GUI.nodeType.recog;
                break;
            default:
                break;
        }
    }//GEN-LAST:event_cmb_NodeTypeActionPerformed

    private void cmb_GraphTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_GraphTypeActionPerformed
        // TODO add your handling code here:
        int i = cmb_GraphType.getSelectedIndex();
        switch (i) {
            case 1:
                graphmode = GUI.graphType.dw;
                break;
            case 0:
                graphmode = GUI.graphType.uw;
                break;
            default:
                break;
        }
    }//GEN-LAST:event_cmb_GraphTypeActionPerformed

    private void cmb_InputTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_InputTypeActionPerformed
        // TODO add your handling code here:
        int i = cmb_InputType.getSelectedIndex();
        switch (i) {
            case 0:
                inputmode = GUI.inputType.numpad;
                break;
            case 1:
                inputmode = GUI.inputType.voice;
                break;
            default:
                break;
        }
    }//GEN-LAST:event_cmb_InputTypeActionPerformed

    private void cmb_AlgoTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_AlgoTypeActionPerformed
        // TODO add your handling code here:
        int i = cmb_AlgoType.getSelectedIndex();
        cmb_GraphType.setEnabled(true);
        cmb_GraphType.setSelectedIndex(0);
        switch (i) {
            case 0:
                algoMode = GUI.algoType.prims;
                cmb_GraphType.setEnabled(false);
                cmb_GraphType.setSelectedIndex(0); // set the index of this to become Undirected - Weighted
                break;
            case 1:
                cmb_GraphType.setEnabled(false);
                cmb_GraphType.setSelectedIndex(0); // set the index of this to become Undirected - Weighted
                algoMode = GUI.algoType.kruskals;
                break;
            case 2:
                algoMode = GUI.algoType.dijkstras;
                break;
            default:
                break;
        }
    }//GEN-LAST:event_cmb_AlgoTypeActionPerformed

    private void chk_TeachingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_TeachingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chk_TeachingActionPerformed

    private void chk_AdvancedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chk_AdvancedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chk_AdvancedActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Load;
    private javax.swing.JButton btn_Reset;
    private javax.swing.JButton btn_Start;
    private javax.swing.JCheckBox chk_Advanced;
    private javax.swing.JCheckBox chk_Teaching;
    private javax.swing.JComboBox cmb_AlgoType;
    private javax.swing.JComboBox cmb_GraphType;
    private javax.swing.JComboBox cmb_InputType;
    private javax.swing.JComboBox cmb_NodeType;
    private javax.swing.JFileChooser fileChooser_Graph;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
