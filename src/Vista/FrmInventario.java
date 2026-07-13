package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsInventario;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmInventario extends javax.swing.JFrame {

    public FrmInventario() {
        initComponents();
        this.llenarCboProd();
    }

    ClsInventario objInv = new ClsInventario();
    boolean cargando = false;

    public void llenarCboProd() {
        cargando = true;
        CboProdInv.removeAllItems();
        CboProdInv.addItem("Todos...");
        objInv.llenarCboProd();
        try {
            while (objInv.datosProd.next()) {
                CboProdInv.addItem(objInv.datosProd.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
        cargando = false;
    }

    public void CrearTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Id Producto");
        modelo.addColumn("Cantidad");
        TblInv.setModel(modelo);
    }

    public void ConsultarInventario() {
        try {
            CrearTabla();
            DefaultTableModel modelo = (DefaultTableModel) TblInv.getModel();

            String seleccion = CboProdInv.getSelectedItem().toString();

            if (seleccion.equals("Todos...")) {
                objInv.ConsultarTodos();
            } else {
                objInv.setNomProdFiltro(seleccion);
                objInv.ConsultarPorProducto();
            }

            while (objInv.datosInventario.next()) {
                Object[] fila = {
                    objInv.datosInventario.getString(1),
                    objInv.datosInventario.getString(2),
                    objInv.datosInventario.getString(3)
                };
                modelo.addRow(fila);
            }

            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No hay registros en el inventario");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar inventario: " + e);
        }
    }

    public void Nuevo() {
        CrearTabla();
        cargando = true;
        CboProdInv.setSelectedIndex(0);
        cargando = false;
    }

    public void Regresar() {
        FrmIndex objFrmIndex = new FrmIndex();
        objFrmIndex.setVisible(true);
        this.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlbTitGesInv = new javax.swing.JLabel();
        JlbProdInv = new javax.swing.JLabel();
        CboProdInv = new javax.swing.JComboBox<>();
        BtnConsultar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblInv = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Inventario ");
        setResizable(false);

        JlbTitGesInv.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesInv.setText("Gestionar Inventario ");

        JlbProdInv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbProdInv.setText("Producto");

        CboProdInv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboProdInv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos..." }));
        CboProdInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboProdInvActionPerformed(evt);
            }
        });

        BtnConsultar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnConsultar.setText("Consultar Iventario");
        BtnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConsultarActionPerformed(evt);
            }
        });

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        BtnRegresar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        BtnRegresar.setText("Regresar");
        BtnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegresarActionPerformed(evt);
            }
        });

        TblInv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Id Producto", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TblInv);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtnConsultar)
                                    .addComponent(JlbProdInv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CboProdInv, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtnNuevo)
                                        .addGap(18, 18, 18)
                                        .addComponent(BtnRegresar))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(253, 253, 253)
                        .addComponent(JlbTitGesInv)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(JlbTitGesInv)
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbProdInv)
                    .addComponent(CboProdInv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnConsultar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnRegresar))
                .addGap(56, 56, 56)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarActionPerformed
        this.ConsultarInventario();
    }//GEN-LAST:event_BtnConsultarActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        this.Nuevo();
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegresarActionPerformed
        this.Regresar();
    }//GEN-LAST:event_BtnRegresarActionPerformed

    private void CboProdInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboProdInvActionPerformed

    }//GEN-LAST:event_CboProdInvActionPerformed

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
            java.util.logging.Logger.getLogger(FrmInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmInventario.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmInventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnConsultar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnRegresar;
    private javax.swing.JComboBox<String> CboProdInv;
    private javax.swing.JLabel JlbProdInv;
    private javax.swing.JLabel JlbTitGesInv;
    private javax.swing.JTable TblInv;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
