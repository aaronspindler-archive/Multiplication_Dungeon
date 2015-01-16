package game;

import java.awt.Toolkit;
import java.io.BufferedReader;
import javax.swing.UIManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;
import resources.SettingsProperties;

public class SchoolProjectForm extends javax.swing.JFrame {

    public SchoolProjectForm() {
        initComponents();
        this.setSize(860, 682);
        if (SettingsProperties.aaronsLaptop == true) {
            this.setResizable(true);
        } else {
            this.setResizable(false);
        }
        this.setTitle(SettingsProperties.gameName);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        gamePanel = new game.GamePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(910, 710));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

    }//GEN-LAST:event_formComponentResized

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        int lebar = this.getWidth() / 2;
        int tinggi = this.getHeight() / 2;
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - lebar;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - tinggi;
        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    public void saveState() {
        FileWriter fw = null;
        try {
            long millis;
            long currMillis = System.currentTimeMillis();
            System.out.println("saving state");
            GameEngine gecopy = gamePanel.ge;
            try {
                FileOutputStream fout = new FileOutputStream("saveState.dat");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(gecopy);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long endMillis = System.currentTimeMillis();
            millis = endMillis - currMillis;
            System.out.println("save completed (" + millis + " milliseconds)");
            File md5f = new File("saveState_md5.dat");
            fw = new FileWriter(md5f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(md5Hash(new File("saveState.dat")));
            bw.close();
            //MD5 checksum, for integrity verification on transport
        } catch (IOException ex) {
            Logger.getLogger(SchoolProjectForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(SchoolProjectForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadState() {
        FileReader fr = null;
        System.out.print("Verifying file md5 hash...");
        try {
            File md5f = new File("saveState_md5.dat");
            fr = new FileReader(md5f);
            BufferedReader br = new BufferedReader(fr);
            String md5Check = br.readLine();
            if (md5Check.equals(md5Hash(new File("saveState.dat")))) {
                gamePanel.lt.listening = false;
                System.out.println("OK, reloading state");
                long millis;
                long currMillis = System.currentTimeMillis();
                try {
                    FileInputStream fin = new FileInputStream("saveState.dat");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    gamePanel.ge = (GameEngine) ois.readObject();
                    ois.close();
                    gamePanel.ge.loadResources();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long endMillis = System.currentTimeMillis();
                millis = endMillis - currMillis;
                System.out.println("load completed (" + millis + " milliseconds)");
                gamePanel.reloadEngine();
            } else {
                System.out.print("md5 check failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("md5 check failed");
        }
    }

    public String md5Hash(File f) {
        String output = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            InputStream is = new FileInputStream(f);
            byte[] head = new byte[8192];
            int read = 0;
            try {
                while ((read = is.read(head)) > 0) {
                    digest.update(head, 0, read);
                }
                byte[] md5sum = digest.digest();
                BigInteger bigInt = new BigInteger(1, md5sum);
                output = bigInt.toString(16);
                System.out.println("MD5 hash: " + output);
                return output;
            } catch (IOException e) {
                throw new RuntimeException("MD5 hash failed", e);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException("Input stream close failure", e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
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
            java.util.logging.Logger.getLogger(SchoolProjectForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SchoolProjectForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private game.GamePanel gamePanel;
    private javax.swing.JSpinner jSpinner1;
    // End of variables declaration//GEN-END:variables

}
