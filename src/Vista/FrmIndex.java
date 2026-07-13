package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import Controlador.ClsSesion;
import Vista.FrmCliente;
import Vista.FrmCompra;
import Vista.FrmCotizacion;
import Vista.FrmInventario;
import Vista.FrmProducto;
import Vista.FrmProveedor;
import Vista.FrmRol;
import Vista.FrmUsuario;
import Vista.FrmVenta;

public class FrmIndex extends javax.swing.JFrame {

    public FrmIndex() {
        initComponents();
        this.aplicarPermisos();
    }

//Metodos
    public void aplicarPermisos() {
        if (ClsSesion.esAdministrador()) {
            BtnGesCli.setVisible(true);
            BtnGesCom.setVisible(true);
            BtnGesCot.setVisible(true);
            BtnGesInv.setVisible(true);
            BtnGesProd.setVisible(true);
            BtnGesProv.setVisible(true);
            BtnGesRol.setVisible(true);
            BtnGesUsu.setVisible(true);
            BtnGesVent.setVisible(true);

        } else if (ClsSesion.esMantenimiento()) {
            BtnGesCli.setVisible(false);
            BtnGesCom.setVisible(false);
            BtnGesCot.setVisible(false);
            BtnGesInv.setVisible(true);
            BtnGesProd.setVisible(false);
            BtnGesProv.setVisible(false);
            BtnGesRol.setVisible(false);
            BtnGesUsu.setVisible(false);
            BtnGesVent.setVisible(false);

        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Sesión no válida");
            FrmLogin objFrmLogin = new FrmLogin();
            objFrmLogin.setVisible(true);
            this.dispose();
        }
    }

    public void AbrirProbarConexion() {
        ClsConexion objClsConexionCitas = new ClsConexion();
        objClsConexionCitas.Conectar();

    }

    public void AbrirCliente() {
        FrmCliente objFrmCliente = new FrmCliente();
        objFrmCliente.setVisible(true);
        this.dispose();
    }

    public void AbrirCompra() {
        FrmCompra objFrmCompra = new FrmCompra();
        objFrmCompra.setVisible(true);
        this.dispose();
    }

    public void AbrirCotizacion() {
        FrmCotizacion objFrmCotizacion = new FrmCotizacion();
        objFrmCotizacion.setVisible(true);
        this.dispose();
    }

    public void AbrirInventario() {
        FrmInventario objFrmInventario = new FrmInventario();
        objFrmInventario.setVisible(true);
        this.dispose();
    }

    public void AbrirProducto() {
        FrmProducto objFrmProducto = new FrmProducto();
        objFrmProducto.setVisible(true);
        this.dispose();
    }

    public void AbrirProveedor() {
        FrmProveedor objFrmProveedor = new FrmProveedor();
        objFrmProveedor.setVisible(true);
        this.dispose();
    }

    public void AbrirRol() {
        FrmRol objFrmRol = new FrmRol();
        objFrmRol.setVisible(true);
        this.dispose();
    }

    public void AbrirUsuario() {
        FrmUsuario objFrmUsuario = new FrmUsuario();
        objFrmUsuario.setVisible(true);
        this.dispose();
    }

    public void AbrirVenta() {
        FrmVenta objFrmVenta = new FrmVenta();
        objFrmVenta.setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlbTitIndex = new javax.swing.JLabel();
        BtnGesCli = new javax.swing.JButton();
        BtnGesCom = new javax.swing.JButton();
        BtnGesCot = new javax.swing.JButton();
        BtnGesInv = new javax.swing.JButton();
        BtnGesProd = new javax.swing.JButton();
        BtnGesProv = new javax.swing.JButton();
        BtnGesRol = new javax.swing.JButton();
        BtnGesUsu = new javax.swing.JButton();
        BtnGesVent = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Index");
        setResizable(false);

        JlbTitIndex.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        JlbTitIndex.setText("Index");

        BtnGesCli.setText("Gestionar Cliente");
        BtnGesCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesCliActionPerformed(evt);
            }
        });

        BtnGesCom.setText("Gestionar Compra");
        BtnGesCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesComActionPerformed(evt);
            }
        });

        BtnGesCot.setText("Gestionar Cotización");
        BtnGesCot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesCotActionPerformed(evt);
            }
        });

        BtnGesInv.setText("Gestionar Inventario");
        BtnGesInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesInvActionPerformed(evt);
            }
        });

        BtnGesProd.setText("Gestionar Producto");
        BtnGesProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesProdActionPerformed(evt);
            }
        });

        BtnGesProv.setText("Gestionar Proveedor");
        BtnGesProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesProvActionPerformed(evt);
            }
        });

        BtnGesRol.setText("Gestionar Rol");
        BtnGesRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesRolActionPerformed(evt);
            }
        });

        BtnGesUsu.setText("Gestionar Usuario");
        BtnGesUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesUsuActionPerformed(evt);
            }
        });

        BtnGesVent.setText("Gestionar Venta");
        BtnGesVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGesVentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtnGesCot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesCli, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtnGesInv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnGesVent, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(JlbTitIndex)))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(JlbTitIndex)
                .addGap(48, 48, 48)
                .addComponent(BtnGesCli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesCom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesCot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesInv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesProv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesRol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesVent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnGesUsu)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGesCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesCliActionPerformed
        this.AbrirCliente();
    }//GEN-LAST:event_BtnGesCliActionPerformed

    private void BtnGesComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesComActionPerformed
        this.AbrirCompra();
    }//GEN-LAST:event_BtnGesComActionPerformed

    private void BtnGesCotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesCotActionPerformed
        this.AbrirCotizacion();
    }//GEN-LAST:event_BtnGesCotActionPerformed

    private void BtnGesInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesInvActionPerformed
        this.AbrirInventario();
    }//GEN-LAST:event_BtnGesInvActionPerformed

    private void BtnGesProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesProdActionPerformed
        this.AbrirProducto();
    }//GEN-LAST:event_BtnGesProdActionPerformed

    private void BtnGesProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesProvActionPerformed
        this.AbrirProveedor();
    }//GEN-LAST:event_BtnGesProvActionPerformed

    private void BtnGesRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesRolActionPerformed
        this.AbrirRol();
    }//GEN-LAST:event_BtnGesRolActionPerformed

    private void BtnGesVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesVentActionPerformed
        this.AbrirVenta();
    }//GEN-LAST:event_BtnGesVentActionPerformed

    private void BtnGesUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGesUsuActionPerformed
        this.AbrirUsuario();
    }//GEN-LAST:event_BtnGesUsuActionPerformed

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
            java.util.logging.Logger.getLogger(FrmIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmIndex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGesCli;
    private javax.swing.JButton BtnGesCom;
    private javax.swing.JButton BtnGesCot;
    private javax.swing.JButton BtnGesInv;
    private javax.swing.JButton BtnGesProd;
    private javax.swing.JButton BtnGesProv;
    private javax.swing.JButton BtnGesRol;
    private javax.swing.JButton BtnGesUsu;
    private javax.swing.JButton BtnGesVent;
    private javax.swing.JLabel JlbTitIndex;
    // End of variables declaration//GEN-END:variables
}
