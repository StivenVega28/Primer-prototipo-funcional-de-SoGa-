package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsProducto;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmProducto extends javax.swing.JFrame {

    public FrmProducto() {
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

    ClsProducto objProd = new ClsProducto();

    public void Bloquear() {
        TxtIdProd.setEnabled(false);
        TxtNomProd.setEnabled(false);
        TxtDescProd.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtIdProd.setEnabled(false);
        TxtNomProd.setEnabled(true);
        TxtDescProd.setEnabled(true);
        BtnRegistrar.setEnabled(true);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    public void Limpiar() {
        TxtIdProd.setText("");
        TxtNomProd.setText("");
        TxtDescProd.setText("");
    }

    public boolean Validar() {
        if (TxtNomProd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el nombre del producto");
            TxtNomProd.requestFocus();
            return false;
        } else if (TxtDescProd.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la descripción del producto");
            TxtDescProd.requestFocus();
            return false;
        }
        return true;
    }

    public void Nuevo() {
        try {
            this.Desbloquear();
            this.Limpiar();
            objProd.Consecutivo();
            if (objProd.datosConsecutivo.next() == true) {
                TxtIdProd.setText(objProd.datosConsecutivo.getString(1));
            }
            BtnActualizar.setEnabled(false);
            BtnEliminar.setEnabled(false);
            TxtNomProd.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar consecutivo: " + e);
        }
    }

    public void Registrar() {
        if (this.Validar() == true) {
            objProd.setNomProd(TxtNomProd.getText().trim());
            objProd.setDesProd(TxtDescProd.getText().trim());
            objProd.Registrar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Buscar() {
        try {
            String busPord = JOptionPane.showInputDialog("Digite el ID del producto que desea buscar");

            if (busPord == null || busPord.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el ID del producto");
            } else {
                objProd.setIdProd(Integer.parseInt(busPord.trim()));
                objProd.Buscar();

                if (objProd.datosProducto.next() == true) {
                    TxtIdProd.setText(objProd.datosProducto.getString(1));
                    TxtNomProd.setText(objProd.datosProducto.getString(2));
                    TxtDescProd.setText(objProd.datosProducto.getString(3));

                    this.Desbloquear();
                    BtnRegistrar.setEnabled(false);
                    BtnNuevo.setEnabled(true);
                    BtnBuscar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "El producto no existe");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del producto debe ser numérico");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + e);
        }
    }

    public void Actualizar() {
        if (this.Validar() == true) {
            objProd.setIdProd(Integer.parseInt(TxtIdProd.getText().trim()));
            objProd.setNomProd(TxtNomProd.getText().trim());
            objProd.setDesProd(TxtDescProd.getText().trim());
            objProd.Actualizar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Eliminar() {
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objProd.setIdProd(Integer.parseInt(TxtIdProd.getText().trim()));
            objProd.Eliminar();

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

        JlbTitGesProd = new javax.swing.JLabel();
        JlbIdProd = new javax.swing.JLabel();
        JlbNomProd = new javax.swing.JLabel();
        JlbDescProd = new javax.swing.JLabel();
        TxtIdProd = new javax.swing.JTextField();
        TxtNomProd = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtDescProd = new javax.swing.JTextArea();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Producto");
        setResizable(false);

        JlbTitGesProd.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesProd.setText("Gestionar Producto");

        JlbIdProd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdProd.setText("Id");

        JlbNomProd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomProd.setText("Nombre");

        JlbDescProd.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDescProd.setText("Descripción");

        TxtIdProd.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtNomProd.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtDescProd.setColumns(20);
        TxtDescProd.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtDescProd.setRows(5);
        jScrollPane1.setViewportView(TxtDescProd);

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
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtnRegistrar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnNuevo)
                                .addGap(18, 18, 18)
                                .addComponent(BtnBuscar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnActualizar)
                                .addGap(18, 18, 18)
                                .addComponent(BtnEliminar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(138, 138, 138)
                                .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JlbNomProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbDescProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(JlbIdProd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(83, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(JlbTitGesProd)
                        .addGap(220, 220, 220))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(JlbTitGesProd)
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbIdProd)
                    .addComponent(TxtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomProd)
                    .addComponent(TxtNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlbDescProd)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRegistrar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar))
                .addGap(18, 18, 18)
                .addComponent(BtnRegresar)
                .addGap(53, 53, 53))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FrmProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProducto().setVisible(true);
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
    private javax.swing.JLabel JlbDescProd;
    private javax.swing.JLabel JlbIdProd;
    private javax.swing.JLabel JlbNomProd;
    private javax.swing.JLabel JlbTitGesProd;
    private javax.swing.JTextArea TxtDescProd;
    private javax.swing.JTextField TxtIdProd;
    private javax.swing.JTextField TxtNomProd;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
