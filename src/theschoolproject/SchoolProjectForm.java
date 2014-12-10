package theschoolproject;

import javax.swing.UIManager;
import flexjson.JSONSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import resources.SettingsProperties;

public class SchoolProjectForm extends javax.swing.JFrame {

    //Variables
    
    JSONSerializer jSerial = new JSONSerializer();

    public SchoolProjectForm() {
        initComponents();
        this.setSize(855, 700);
        if(SettingsProperties.aaronsLaptop == true)
        {
            this.setResizable(true);
        }
        else
        {
            this.setResizable(false);
        }
        this.setTitle(SettingsProperties.gameName);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        gamePanel = new theschoolproject.GamePanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveStateBtn = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        documentationBtn = new javax.swing.JMenuItem();
        aboutbtn = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(910, 710));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        getContentPane().setLayout(null);

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );

        getContentPane().add(gamePanel);
        gamePanel.setBounds(0, 0, 850, 650);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        saveStateBtn.setText("Save State");
        saveStateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveStateBtnActionPerformed(evt);
            }
        });
        fileMenu.add(saveStateBtn);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText("Help");

        documentationBtn.setText("Documentation");
        documentationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentationBtnActionPerformed(evt);
            }
        });
        jMenu1.add(documentationBtn);

        aboutbtn.setText("About");
        aboutbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutbtnActionPerformed(evt);
            }
        });
        jMenu1.add(aboutbtn);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

    }//GEN-LAST:event_formComponentResized

    private void aboutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutbtnActionPerformed

    }//GEN-LAST:event_aboutbtnActionPerformed

    private void documentationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentationBtnActionPerformed
        UsefulSnippets.openWebpage("https://github.com/xNovax/Multiplication_Dungeon/wiki");
        System.out.println("Opened webpage");
    }//GEN-LAST:event_documentationBtnActionPerformed

    private void saveStateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveStateBtnActionPerformed
        saveState();
    }//GEN-LAST:event_saveStateBtnActionPerformed

    public void saveState(){
        String savedState = jSerial.deepSerialize(gamePanel);
        try {
            File f = new File("ss01.dat");
            FileWriter fw = new FileWriter(f); 
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(savedState);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SchoolProjectForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutbtn;
    private javax.swing.JMenuItem documentationBtn;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private theschoolproject.GamePanel gamePanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem saveStateBtn;
    // End of variables declaration//GEN-END:variables

}
