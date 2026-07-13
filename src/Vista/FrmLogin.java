package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsLogin;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmLogin extends javax.swing.JFrame {

    public FrmLogin() {
        initComponents();
    }

    ClsLogin objLogin = new ClsLogin();

    public void Ingresar() {
        try {
            String usuario = TxtUsuIniSes.getText().trim();
            String contrasena = new String(TxtContIniSes.getPassword()).trim();

            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el usuario");
                TxtUsuIniSes.requestFocus();
                return;
            }

            if (contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite la contraseña");
                TxtContIniSes.requestFocus();
                return;
            }

            objLogin.setUsuLogin(usuario);
            objLogin.setConLogin(contrasena);
            objLogin.Validar();

            if (objLogin.datosLogin.next() == true) {
                String rol = objLogin.datosLogin.getString(1); // nomRolUsu

                // Asignar sesión
                ClsSesion.usuarioActivo = usuario;

                switch (rol) {
                    case "Administrador":
                        ClsSesion.permiso = ClsSesion.Permiso.ADMINISTRADOR;
                        break;
                    case "Mantenimiento":
                        ClsSesion.permiso = ClsSesion.Permiso.MANTENIMIENTO;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Rol no reconocido: " + rol);
                        return;
                }

                // Abrir el index
                FrmIndex objFrmIndex = new FrmIndex();
                objFrmIndex.setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                TxtUsuIniSes.setText("");
                TxtContIniSes.setText("");
                TxtUsuIniSes.requestFocus();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlbTitIniSes = new javax.swing.JLabel();
        JlbUsuIniSes = new javax.swing.JLabel();
        TxtUsuIniSes = new javax.swing.JTextField();
        JlbContIniSes = new javax.swing.JLabel();
        TxtContIniSes = new javax.swing.JPasswordField();
        BtnIngIniSes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Iniciar sesion");
        setResizable(false);

        JlbTitIniSes.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitIniSes.setText("Iniciar sesion");

        JlbUsuIniSes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbUsuIniSes.setText("Usuario");

        TxtUsuIniSes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtUsuIniSes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtUsuIniSesKeyPressed(evt);
            }
        });

        JlbContIniSes.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbContIniSes.setText("Constraseña");

        TxtContIniSes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TxtContIniSesKeyPressed(evt);
            }
        });

        BtnIngIniSes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnIngIniSes.setText("Ingresar");
        BtnIngIniSes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIngIniSesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlbUsuIniSes, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlbContIniSes, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxtUsuIniSes)
                    .addComponent(TxtContIniSes, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                .addGap(76, 76, 76))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(JlbTitIniSes, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(BtnIngIniSes, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(JlbTitIniSes)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbUsuIniSes)
                    .addComponent(TxtUsuIniSes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbContIniSes)
                    .addComponent(TxtContIniSes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addComponent(BtnIngIniSes)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtContIniSesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtContIniSesKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            this.Ingresar();
        }
    }//GEN-LAST:event_TxtContIniSesKeyPressed

    private void TxtUsuIniSesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtUsuIniSesKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            TxtContIniSes.requestFocus();
        }
    }//GEN-LAST:event_TxtUsuIniSesKeyPressed

    private void BtnIngIniSesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIngIniSesActionPerformed
        this.Ingresar();
    }//GEN-LAST:event_BtnIngIniSesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmLogin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnIngIniSes;
    private javax.swing.JLabel JlbContIniSes;
    private javax.swing.JLabel JlbTitIniSes;
    private javax.swing.JLabel JlbUsuIniSes;
    private javax.swing.JPasswordField TxtContIniSes;
    private javax.swing.JTextField TxtUsuIniSes;
    // End of variables declaration//GEN-END:variables
}
