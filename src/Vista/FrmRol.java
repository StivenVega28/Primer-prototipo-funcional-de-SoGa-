package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsRol;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmRol extends javax.swing.JFrame {

    public FrmRol() {
        initComponents();
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para acceder a este módulo");
            this.dispose();
            return;
        }
        this.Bloquear();
        BtnNuevo.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    ClsRol objRol = new ClsRol();

    public void Bloquear() {
        TxtIdRol.setEnabled(false);
        TxtNomRol.setEnabled(false);
        CboPermRol.setEnabled(false);
        TxtDescRol.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtIdRol.setEnabled(false);
        TxtNomRol.setEnabled(true);
        CboPermRol.setEnabled(true);
        TxtDescRol.setEnabled(true);
        BtnRegistrar.setEnabled(true);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    public void Limpiar() {
        TxtIdRol.setText("");
        TxtNomRol.setText("");
        CboPermRol.setSelectedIndex(0);
        TxtDescRol.setText("");
    }

    public boolean Validar() {
        if (TxtNomRol.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el nombre del rol");
            TxtNomRol.requestFocus();
            return false;
        } else if (CboPermRol.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione los permisos del rol");
            CboPermRol.requestFocus();
            return false;
        } else if (TxtDescRol.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la descripción del rol");
            TxtDescRol.requestFocus();
            return false;
        }
        return true;
    }

    public void Nuevo() {
        try {
            this.Desbloquear();
            this.Limpiar();
            objRol.Consecutivo();
            if (objRol.datosConsecutivo.next() == true) {
                TxtIdRol.setText(objRol.datosConsecutivo.getString(1));
            }
            BtnActualizar.setEnabled(false);
            BtnEliminar.setEnabled(false);
            TxtNomRol.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar consecutivo: " + e);
        }
    }

    public void Registrar() {
        if (this.Validar() == true) {
            objRol.setNomRol(TxtNomRol.getText().trim());
            objRol.setPermRol(CboPermRol.getSelectedItem().toString());
            objRol.setDescRol(TxtDescRol.getText().trim());
            objRol.Registrar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Buscar() {
        try {
            String busRol = JOptionPane.showInputDialog("Digite el ID del rol que desea buscar");

            if (busRol == null || busRol.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el ID del rol");
            } else {
                objRol.setIdRol(Integer.parseInt(busRol.trim()));
                objRol.Buscar();

                if (objRol.datosRol.next() == true) {
                    TxtIdRol.setText(objRol.datosRol.getString(1));
                    TxtNomRol.setText(objRol.datosRol.getString(2));
                    CboPermRol.setSelectedItem(objRol.datosRol.getString(3));
                    TxtDescRol.setText(objRol.datosRol.getString(4));

                    this.Desbloquear();
                    BtnRegistrar.setEnabled(false);
                    BtnNuevo.setEnabled(false);
                    BtnBuscar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "El rol no existe");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del rol debe ser numérico");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el rol: " + e);
        }
    }

    public void Actualizar() {
        if (this.Validar() == true) {
            objRol.setIdRol(Integer.parseInt(TxtIdRol.getText().trim()));
            objRol.setNomRol(TxtNomRol.getText().trim());
            objRol.setPermRol(CboPermRol.getSelectedItem().toString());
            objRol.setDescRol(TxtDescRol.getText().trim());
            objRol.Actualizar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Eliminar() {
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar este rol?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objRol.setIdRol(Integer.parseInt(TxtIdRol.getText().trim()));
            objRol.Eliminar();

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

        JlbTitGesRol = new javax.swing.JLabel();
        JlbIdRol = new javax.swing.JLabel();
        JlbNomRol = new javax.swing.JLabel();
        JlbPermRol = new javax.swing.JLabel();
        JlbDescRol = new javax.swing.JLabel();
        TxtIdRol = new javax.swing.JTextField();
        TxtNomRol = new javax.swing.JTextField();
        CboPermRol = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtDescRol = new javax.swing.JTextArea();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Rol");
        setResizable(false);

        JlbTitGesRol.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesRol.setText("Gestionar Rol");

        JlbIdRol.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdRol.setText("Id");

        JlbNomRol.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomRol.setText("Nombre");

        JlbPermRol.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbPermRol.setText("Permisos");

        JlbDescRol.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDescRol.setText("Descripción");

        TxtIdRol.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtNomRol.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        CboPermRol.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboPermRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "Administrador", "Mantenimiento" }));
        CboPermRol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboPermRolActionPerformed(evt);
            }
        });

        TxtDescRol.setColumns(20);
        TxtDescRol.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtDescRol.setRows(5);
        jScrollPane1.setViewportView(TxtDescRol);

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
        BtnBuscar.setText("Buscar");
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
                        .addGap(241, 241, 241)
                        .addComponent(JlbTitGesRol))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(JlbIdRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbNomRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbPermRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbDescRol, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(CboPermRol, javax.swing.GroupLayout.Alignment.LEADING, 0, 160, Short.MAX_VALUE)
                                        .addComponent(TxtNomRol, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(TxtIdRol, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtnRegistrar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(BtnBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnActualizar)
                                .addGap(27, 27, 27)
                                .addComponent(BtnEliminar)))))
                .addGap(93, 93, 93))
            .addGroup(layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(JlbTitGesRol)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbIdRol)
                    .addComponent(TxtIdRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomRol)
                    .addComponent(TxtNomRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbPermRol)
                    .addComponent(CboPermRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlbDescRol)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRegistrar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar))
                .addGap(26, 26, 26)
                .addComponent(BtnRegresar)
                .addGap(46, 46, 46))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CboPermRolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboPermRolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CboPermRolActionPerformed

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
            java.util.logging.Logger.getLogger(FrmRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmRol.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmRol().setVisible(true);
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
    private javax.swing.JComboBox<String> CboPermRol;
    private javax.swing.JLabel JlbDescRol;
    private javax.swing.JLabel JlbIdRol;
    private javax.swing.JLabel JlbNomRol;
    private javax.swing.JLabel JlbPermRol;
    private javax.swing.JLabel JlbTitGesRol;
    private javax.swing.JTextArea TxtDescRol;
    private javax.swing.JTextField TxtIdRol;
    private javax.swing.JTextField TxtNomRol;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
