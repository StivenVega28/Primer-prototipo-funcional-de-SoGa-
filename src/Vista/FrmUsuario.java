package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsUsuario;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmUsuario extends javax.swing.JFrame {

    public FrmUsuario() {
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

    ClsUsuario objUsu = new ClsUsuario();

    public void Bloquear() {
        TxtIdUsu.setEnabled(false);
        CboRolUsu.setEnabled(false);
        TxtNomUsu.setEnabled(false);
        TxtApeUsu.setEnabled(false);
        TxtTelUsu.setEnabled(false);
        TxtCorUsu.setEnabled(false);
        TxtConUsu.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtIdUsu.setEnabled(false);
        CboRolUsu.setEnabled(true);
        TxtNomUsu.setEnabled(true);
        TxtApeUsu.setEnabled(true);
        TxtTelUsu.setEnabled(true);
        TxtCorUsu.setEnabled(true);
        TxtConUsu.setEnabled(true);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnRegresar.setEnabled(true);

        // Solo administrador puede actualizar y eliminar
        BtnRegistrar.setEnabled(ClsSesion.esAdministrador());
        BtnActualizar.setEnabled(ClsSesion.esAdministrador());
        BtnEliminar.setEnabled(ClsSesion.esAdministrador());
    }

    public void Limpiar() {
        TxtIdUsu.setText("");
        CboRolUsu.setSelectedIndex(0);
        TxtNomUsu.setText("");
        TxtApeUsu.setText("");
        TxtTelUsu.setText("");
        TxtCorUsu.setText("");
        TxtConUsu.setText("");
    }

    public void llenarCboRol() {
        CboRolUsu.removeAllItems();
        CboRolUsu.addItem("Seleccione...");
        objUsu.llenarCboRol();
        try {
            while (objUsu.datosRol.next() == true) {
                CboRolUsu.addItem(objUsu.datosRol.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar roles: " + e);
        }
    }

    public boolean Validar() {
        if (CboRolUsu.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el rol del usuario");
            CboRolUsu.requestFocus();
            return false;
        } else if (TxtNomUsu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el nombre del usuario");
            TxtNomUsu.requestFocus();
            return false;
        } else if (TxtApeUsu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el apellido del usuario");
            TxtApeUsu.requestFocus();
            return false;
        } else if (TxtTelUsu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el teléfono del usuario");
            TxtTelUsu.requestFocus();
            return false;
        } else if (TxtCorUsu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el correo del usuario");
            TxtCorUsu.requestFocus();
            return false;
        } else if (TxtConUsu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la contraseña del usuario");
            TxtConUsu.requestFocus();
            return false;
        }
        return true;
    }

    public void Nuevo() {
        try {
            if (!ClsSesion.esAdministrador()) {
                JOptionPane.showMessageDialog(null, "No tiene permisos para registrar usuarios");
                return;
            }
            this.Desbloquear();
            this.Limpiar();
            this.llenarCboRol();
            objUsu.Consecutivo();
            if (objUsu.datosConsecutivo.next() == true) {
                TxtIdUsu.setText(objUsu.datosConsecutivo.getString(1));
            }
            BtnActualizar.setEnabled(false);
            BtnEliminar.setEnabled(false);
            TxtNomUsu.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar consecutivo: " + e);
        }
    }

    public void Registrar() {
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para registrar usuarios");
            return;
        }
        if (this.Validar() == true) {
            try {
                objUsu.setNomRolBus(CboRolUsu.getSelectedItem().toString());
                objUsu.BuscarIdRol();
                if (objUsu.datosIdRol.next() == true) {
                    objUsu.setIdRolUsu(Integer.parseInt(objUsu.datosIdRol.getString(1)));
                }

                objUsu.setNomRolUsu(CboRolUsu.getSelectedItem().toString());
                objUsu.setNomUsu(TxtNomUsu.getText().trim());
                objUsu.setApeUsu(TxtApeUsu.getText().trim());
                objUsu.setTelUsu(TxtTelUsu.getText().trim());
                objUsu.setCorUsu(TxtCorUsu.getText().trim());
                objUsu.setConUsu(TxtConUsu.getText().trim());
                objUsu.Registrar();

                this.Limpiar();
                this.Bloquear();
                BtnNuevo.setEnabled(true);
                BtnRegresar.setEnabled(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener id del rol: " + e);
            }
        }
    }

    public void Buscar() {
        try {
            String busUsu = JOptionPane.showInputDialog("Digite el ID del usuario que desea buscar");

            if (busUsu == null || busUsu.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el ID del usuario");
            } else {
                objUsu.setIdUsu(Integer.parseInt(busUsu.trim()));
                objUsu.Buscar();

                if (objUsu.datosUsuario.next() == true) {
                    TxtIdUsu.setText(objUsu.datosUsuario.getString(1));
                    this.llenarCboRol();
                    CboRolUsu.setSelectedItem(objUsu.datosUsuario.getString(3));
                    TxtNomUsu.setText(objUsu.datosUsuario.getString(4));
                    TxtApeUsu.setText(objUsu.datosUsuario.getString(5));
                    TxtTelUsu.setText(objUsu.datosUsuario.getString(6));
                    TxtCorUsu.setText(objUsu.datosUsuario.getString(7));
                    TxtConUsu.setText(objUsu.datosUsuario.getString(8));

                    this.Desbloquear();
                    BtnRegistrar.setEnabled(false);
                    BtnNuevo.setEnabled(false);
                    BtnBuscar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario no existe");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID del usuario debe ser numérico");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el usuario: " + e);
        }
    }

    public void Actualizar() {
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para actualizar usuarios");
            return;
        }
        if (this.Validar() == true) {
            try {
                objUsu.setNomRolBus(CboRolUsu.getSelectedItem().toString());
                objUsu.BuscarIdRol();
                if (objUsu.datosIdRol.next() == true) {
                    objUsu.setIdRolUsu(Integer.parseInt(objUsu.datosIdRol.getString(1)));
                }

                objUsu.setIdUsu(Integer.parseInt(TxtIdUsu.getText().trim()));
                objUsu.setNomRolUsu(CboRolUsu.getSelectedItem().toString());
                objUsu.setNomUsu(TxtNomUsu.getText().trim());
                objUsu.setApeUsu(TxtApeUsu.getText().trim());
                objUsu.setTelUsu(TxtTelUsu.getText().trim());
                objUsu.setCorUsu(TxtCorUsu.getText().trim());
                objUsu.setConUsu(TxtConUsu.getText().trim());
                objUsu.Actualizar();

                this.Limpiar();
                this.Bloquear();
                BtnNuevo.setEnabled(true);
                BtnRegresar.setEnabled(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener id del rol: " + e);
            }
        }
    }

    public void Eliminar() {
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para eliminar usuarios");
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar este usuario?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objUsu.setIdUsu(Integer.parseInt(TxtIdUsu.getText().trim()));
            objUsu.Eliminar();

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

        JlbTitGesUsu = new javax.swing.JLabel();
        JlbIdUsu = new javax.swing.JLabel();
        JlbRolUsu = new javax.swing.JLabel();
        JlbNomUsu = new javax.swing.JLabel();
        JlbApeUsu = new javax.swing.JLabel();
        JlbTelUsu = new javax.swing.JLabel();
        JlbCorUsu = new javax.swing.JLabel();
        JlbConUsu = new javax.swing.JLabel();
        TxtIdUsu = new javax.swing.JTextField();
        TxtNomUsu = new javax.swing.JTextField();
        TxtApeUsu = new javax.swing.JTextField();
        TxtTelUsu = new javax.swing.JTextField();
        TxtCorUsu = new javax.swing.JTextField();
        TxtConUsu = new javax.swing.JTextField();
        CboRolUsu = new javax.swing.JComboBox<>();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Usuario");
        setResizable(false);

        JlbTitGesUsu.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesUsu.setText("Gestionar Usuario");

        JlbIdUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdUsu.setText("Id");

        JlbRolUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbRolUsu.setText("Rol");

        JlbNomUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomUsu.setText("Nombre");

        JlbApeUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbApeUsu.setText("Apellido");

        JlbTelUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbTelUsu.setText("Telefono");

        JlbCorUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCorUsu.setText("Correo");

        JlbConUsu.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbConUsu.setText("Contraseña");

        TxtIdUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtNomUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtApeUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtTelUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtCorUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtConUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        CboRolUsu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboRolUsu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 99, Short.MAX_VALUE)
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
                        .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(99, 99, 99))
            .addGroup(layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(JlbIdUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TxtIdUsu))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JlbRolUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JlbNomUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JlbApeUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JlbTelUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JlbCorUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JlbConUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CboRolUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtNomUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtApeUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtTelUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCorUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtConUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JlbTitGesUsu)
                .addGap(223, 223, 223))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(JlbTitGesUsu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbIdUsu)
                    .addComponent(TxtIdUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbRolUsu)
                    .addComponent(CboRolUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomUsu)
                    .addComponent(TxtNomUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbApeUsu)
                    .addComponent(TxtApeUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbTelUsu)
                    .addComponent(TxtTelUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbCorUsu)
                    .addComponent(TxtCorUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbConUsu)
                    .addComponent(TxtConUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRegistrar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar))
                .addGap(18, 18, 18)
                .addComponent(BtnRegresar)
                .addGap(85, 85, 85))
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
            java.util.logging.Logger.getLogger(FrmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmUsuario().setVisible(true);
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
    private javax.swing.JComboBox<String> CboRolUsu;
    private javax.swing.JLabel JlbApeUsu;
    private javax.swing.JLabel JlbConUsu;
    private javax.swing.JLabel JlbCorUsu;
    private javax.swing.JLabel JlbIdUsu;
    private javax.swing.JLabel JlbNomUsu;
    private javax.swing.JLabel JlbRolUsu;
    private javax.swing.JLabel JlbTelUsu;
    private javax.swing.JLabel JlbTitGesUsu;
    private javax.swing.JTextField TxtApeUsu;
    private javax.swing.JTextField TxtConUsu;
    private javax.swing.JTextField TxtCorUsu;
    private javax.swing.JTextField TxtIdUsu;
    private javax.swing.JTextField TxtNomUsu;
    private javax.swing.JTextField TxtTelUsu;
    // End of variables declaration//GEN-END:variables
}
