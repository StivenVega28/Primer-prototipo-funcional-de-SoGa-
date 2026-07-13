package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsCliente;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmCliente extends javax.swing.JFrame {

    public FrmCliente() {
        initComponents();
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para registrar clientes");
            return;
        }
        this.Bloquear();
        BtnNuevo.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    ClsCliente objCli = new ClsCliente();

    public void Bloquear() {
        TxtNitCli.setEnabled(false);
        TxtNomCli.setEnabled(false);
        TxtCiuCli.setEnabled(false);
        TxtSedCli.setEnabled(false);
        TxtDirCli.setEnabled(false);
        TxtTelCli.setEnabled(false);
        TxtCorCli.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtNitCli.setEnabled(true);
        TxtNomCli.setEnabled(true);
        TxtCiuCli.setEnabled(true);
        TxtSedCli.setEnabled(true);
        TxtDirCli.setEnabled(true);
        TxtTelCli.setEnabled(true);
        TxtCorCli.setEnabled(true);
        BtnRegistrar.setEnabled(true);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    public void Limpiar() {
        TxtNitCli.setText("");
        TxtNomCli.setText("");
        TxtCiuCli.setText("");
        TxtSedCli.setText("");
        TxtDirCli.setText("");
        TxtTelCli.setText("");
        TxtCorCli.setText("");
    }

    public boolean Validar() {
        if (TxtNitCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el NIT del cliente");
            TxtNitCli.requestFocus();
            return false;
        } else if (TxtNomCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el nombre del cliente");
            TxtNomCli.requestFocus();
            return false;
        } else if (TxtCiuCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la ciudad del cliente");
            TxtCiuCli.requestFocus();
            return false;
        } else if (TxtSedCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la sede del cliente");
            TxtSedCli.requestFocus();
            return false;
        } else if (TxtDirCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la dirección del cliente");
            TxtDirCli.requestFocus();
            return false;
        } else if (TxtTelCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el teléfono del cliente");
            TxtTelCli.requestFocus();
            return false;
        } else if (TxtCorCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el correo del cliente");
            TxtCorCli.requestFocus();
            return false;
        }
        return true;
    }

    public void Registrar() {
        if (this.Validar() == true) {
            objCli.setNitCli(TxtNitCli.getText().trim());
            objCli.setNomCli(TxtNomCli.getText().trim());
            objCli.setCiuCli(TxtCiuCli.getText().trim());
            objCli.setSedCli(TxtSedCli.getText().trim());
            objCli.setDirCli(TxtDirCli.getText().trim());
            objCli.setTelCli(TxtTelCli.getText().trim());
            objCli.setCorCli(TxtCorCli.getText().trim());
            objCli.Registrar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Actualizar() {
        if (this.Validar() == true) {
            objCli.setNitCli(TxtNitCli.getText().trim());
            objCli.setNomCli(TxtNomCli.getText().trim());
            objCli.setCiuCli(TxtCiuCli.getText().trim());
            objCli.setSedCli(TxtSedCli.getText().trim());
            objCli.setDirCli(TxtDirCli.getText().trim());
            objCli.setTelCli(TxtTelCli.getText().trim());
            objCli.setCorCli(TxtCorCli.getText().trim());
            objCli.Actualizar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Nuevo() {
        this.Desbloquear();
        this.Limpiar();
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        TxtNitCli.requestFocus();
    }

    public void Buscar() {
        try {
            String busCli = JOptionPane.showInputDialog("Digite el NIT del cliente que desea buscar");

            if (busCli == null || busCli.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el NIT del cliente");
            } else {
                objCli.setNitCli(busCli.trim());
                objCli.Buscar();

                if (objCli.datosCliente.next() == true) {
                    TxtNitCli.setText(objCli.datosCliente.getString(1));
                    TxtNomCli.setText(objCli.datosCliente.getString(2));
                    TxtCiuCli.setText(objCli.datosCliente.getString(3));
                    TxtSedCli.setText(objCli.datosCliente.getString(4));
                    TxtDirCli.setText(objCli.datosCliente.getString(5));
                    TxtTelCli.setText(objCli.datosCliente.getString(6));
                    TxtCorCli.setText(objCli.datosCliente.getString(7));

                    this.Desbloquear();
                    TxtNitCli.setEnabled(false);
                    BtnRegistrar.setEnabled(false);
                    BtnBuscar.setEnabled(false);
                    BtnNuevo.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "El cliente no existe");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el cliente: " + e);
        }
    }

    public void Eliminar() {
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar este cliente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objCli.setNitCli(TxtNitCli.getText().trim());
            objCli.Eliminar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Regresar() {
        FrmIndex objFrmIndex = new FrmIndex();
        objFrmIndex.setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlbTitGesCli = new javax.swing.JLabel();
        JlbNitCli = new javax.swing.JLabel();
        JlbNomCli = new javax.swing.JLabel();
        JlbCiuCli = new javax.swing.JLabel();
        JlbSedCli = new javax.swing.JLabel();
        JlbDirCli = new javax.swing.JLabel();
        JlbTelCli = new javax.swing.JLabel();
        JlbCorCli = new javax.swing.JLabel();
        TxtNitCli = new javax.swing.JTextField();
        TxtNomCli = new javax.swing.JTextField();
        TxtCiuCli = new javax.swing.JTextField();
        TxtSedCli = new javax.swing.JTextField();
        TxtDirCli = new javax.swing.JTextField();
        TxtTelCli = new javax.swing.JTextField();
        TxtCorCli = new javax.swing.JTextField();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Cliente");
        setResizable(false);

        JlbTitGesCli.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesCli.setText("Gestionar Cliente");

        JlbNitCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNitCli.setText("Nit");

        JlbNomCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomCli.setText("Nombre");

        JlbCiuCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCiuCli.setText("Ciudad");

        JlbSedCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbSedCli.setText("Sede");

        JlbDirCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDirCli.setText("Direccion");

        JlbTelCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbTelCli.setText("Telefono");

        JlbCorCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCorCli.setText("Correo");

        TxtNitCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtNomCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtCiuCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtSedCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtSedCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtSedCliActionPerformed(evt);
            }
        });

        TxtDirCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtTelCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtCorCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        BtnRegistrar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnRegistrar.setText("Registrar");
        BtnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistrarActionPerformed(evt);
            }
        });

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        BtnBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnBuscar.setText("Buscar ");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnActualizar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnActualizar.setText("Actualizar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        BtnRegresar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnRegresar.setText("Regresar al index");
        BtnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addComponent(JlbTitGesCli))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(BtnRegistrar)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(JlbSedCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbNomCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbCiuCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbDirCli, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(JlbTelCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbCorCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbNitCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(TxtCorCli, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TxtTelCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(TxtDirCli, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TxtSedCli, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TxtCiuCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(TxtNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtNitCli, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnActualizar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnEliminar)))))
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(240, 240, 240))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(JlbTitGesCli)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNitCli)
                    .addComponent(TxtNitCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomCli)
                    .addComponent(TxtNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbCiuCli)
                    .addComponent(TxtCiuCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbSedCli)
                    .addComponent(TxtSedCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbDirCli)
                    .addComponent(TxtDirCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbTelCli)
                    .addComponent(TxtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbCorCli)
                    .addComponent(TxtCorCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRegistrar)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar)
                    .addComponent(BtnNuevo))
                .addGap(30, 30, 30)
                .addComponent(BtnRegresar)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtSedCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtSedCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSedCliActionPerformed

    private void BtnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarActionPerformed
        this.Registrar();
    }//GEN-LAST:event_BtnRegistrarActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        this.Nuevo();
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        this.Buscar();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        this.Actualizar();
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        this.Eliminar();
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegresarActionPerformed
        this.Regresar();
    }//GEN-LAST:event_BtnRegresarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnRegistrar;
    private javax.swing.JButton BtnRegresar;
    private javax.swing.JLabel JlbCiuCli;
    private javax.swing.JLabel JlbCorCli;
    private javax.swing.JLabel JlbDirCli;
    private javax.swing.JLabel JlbNitCli;
    private javax.swing.JLabel JlbNomCli;
    private javax.swing.JLabel JlbSedCli;
    private javax.swing.JLabel JlbTelCli;
    private javax.swing.JLabel JlbTitGesCli;
    private javax.swing.JTextField TxtCiuCli;
    private javax.swing.JTextField TxtCorCli;
    private javax.swing.JTextField TxtDirCli;
    private javax.swing.JTextField TxtNitCli;
    private javax.swing.JTextField TxtNomCli;
    private javax.swing.JTextField TxtSedCli;
    private javax.swing.JTextField TxtTelCli;
    // End of variables declaration//GEN-END:variables

}
