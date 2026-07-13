package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsProveedor;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmProveedor extends javax.swing.JFrame {

    public FrmProveedor() {
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

    ClsProveedor objProv = new ClsProveedor();

    public void Bloquear() {
        TxtNitProv.setEnabled(false);
        TxtNomProv.setEnabled(false);
        TxtCiuProv.setEnabled(false);
        TxtDirProv.setEnabled(false);
        TxtTelProv.setEnabled(false);
        TxtCorProv.setEnabled(false);
        TxtDescProv.setEnabled(false);
        TxtNomProdProv.setEnabled(false);
        TxtIdProdProv.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtNitProv.setEnabled(true);
        TxtNomProv.setEnabled(true);
        TxtCiuProv.setEnabled(true);
        TxtDirProv.setEnabled(true);
        TxtTelProv.setEnabled(true);
        TxtCorProv.setEnabled(true);
        TxtDescProv.setEnabled(true);
        TxtNomProdProv.setEnabled(true);
        TxtIdProdProv.setEnabled(false);
        BtnRegistrar.setEnabled(true);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    public void Limpiar() {
        TxtNitProv.setText("");
        TxtNomProv.setText("");
        TxtCiuProv.setText("");
        TxtDirProv.setText("");
        TxtTelProv.setText("");
        TxtCorProv.setText("");
        TxtDescProv.setText("");
        TxtNomProdProv.setSelectedIndex(0);
        TxtIdProdProv.setText("");
    }

    public void llenarCboProd() {
        TxtNomProdProv.removeAllItems();
        TxtNomProdProv.addItem("Seleccione...");
        objProv.llenarCboProd();
        try {
            while (objProv.datosProd.next() == true) {
                TxtNomProdProv.addItem(objProv.datosProd.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
    }

    public void BuscarIdProd() {
        try {
            if (TxtNomProdProv.getSelectedItem() != null
                    && !TxtNomProdProv.getSelectedItem().toString().equals("Seleccione...")) {
                objProv.setNomProdBus(TxtNomProdProv.getSelectedItem().toString());
                objProv.BuscarIdProd();
                if (objProv.datosIdProd.next() == true) {
                    TxtIdProdProv.setText(objProv.datosIdProd.getString(1));
                }
            } else {
                TxtIdProdProv.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar id del producto: " + e);
        }
    }

    public boolean Validar() {
        if (TxtNitProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el NIT del proveedor");
            TxtNitProv.requestFocus();
            return false;
        } else if (TxtNomProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el nombre del proveedor");
            TxtNomProv.requestFocus();
            return false;
        } else if (TxtCiuProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la ciudad del proveedor");
            TxtCiuProv.requestFocus();
            return false;
        } else if (TxtDirProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la dirección del proveedor");
            TxtDirProv.requestFocus();
            return false;
        } else if (TxtTelProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el teléfono del proveedor");
            TxtTelProv.requestFocus();
            return false;
        } else if (TxtCorProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el correo del proveedor");
            TxtCorProv.requestFocus();
            return false;
        } else if (TxtDescProv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la descripción del proveedor");
            TxtDescProv.requestFocus();
            return false;
        } else if (TxtNomProdProv.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
            TxtNomProdProv.requestFocus();
            return false;
        }
        return true;
    }

    public void Nuevo() {
        this.Desbloquear();
        this.Limpiar();
        this.llenarCboProd();
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        TxtNitProv.requestFocus();
    }

    public void Registrar() {
        if (this.Validar() == true) {
            objProv.setNitProv(TxtNitProv.getText().trim());
            objProv.setIdProdProv(Integer.parseInt(TxtIdProdProv.getText().trim()));
            objProv.setNomProdProv(TxtNomProdProv.getSelectedItem().toString());
            objProv.setNomProv(TxtNomProv.getText().trim());
            objProv.setCiuProv(TxtCiuProv.getText().trim());
            objProv.setDirProv(TxtDirProv.getText().trim());
            objProv.setTelProv(TxtTelProv.getText().trim());
            objProv.setCorProv(TxtCorProv.getText().trim());
            objProv.setDescProv(TxtDescProv.getText().trim());
            objProv.Registrar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Buscar() {
        try {
            String busProv = JOptionPane.showInputDialog("Digite el NIT del proveedor que desea buscar");

            if (busProv == null || busProv.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el NIT del proveedor");
            } else {
                objProv.setNitProv(busProv.trim());
                objProv.Buscar();

                if (objProv.datosProveedor.next() == true) {
                    String nitLeido = objProv.datosProveedor.getString(1);
                    String idProdLeido = objProv.datosProveedor.getString(2);
                    String nomProdLeido = objProv.datosProveedor.getString(3);
                    String nomLeido = objProv.datosProveedor.getString(4);
                    String ciuLeido = objProv.datosProveedor.getString(5);
                    String dirLeido = objProv.datosProveedor.getString(6);
                    String telLeido = objProv.datosProveedor.getString(7);
                    String corLeido = objProv.datosProveedor.getString(8);
                    String descLeido = objProv.datosProveedor.getString(9);

                    this.llenarCboProd();

                    TxtNitProv.setText(nitLeido);
                    TxtIdProdProv.setText(idProdLeido);
                    TxtNomProv.setText(nomLeido);
                    TxtCiuProv.setText(ciuLeido);
                    TxtDirProv.setText(dirLeido);
                    TxtTelProv.setText(telLeido);
                    TxtCorProv.setText(corLeido);
                    TxtDescProv.setText(descLeido);
                    TxtNomProdProv.setSelectedItem(nomProdLeido);

                    this.Desbloquear();
                    TxtNitProv.setEnabled(false);
                    BtnRegistrar.setEnabled(false);
                    BtnNuevo.setEnabled(true);
                    BtnBuscar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "El proveedor no existe");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el proveedor: " + e);
        }
    }

    public void Actualizar() {
        if (this.Validar() == true) {
            objProv.setNitProv(TxtNitProv.getText().trim());
            objProv.setIdProdProv(Integer.parseInt(TxtIdProdProv.getText().trim()));
            objProv.setNomProdProv(TxtNomProdProv.getSelectedItem().toString());
            objProv.setNomProv(TxtNomProv.getText().trim());
            objProv.setCiuProv(TxtCiuProv.getText().trim());
            objProv.setDirProv(TxtDirProv.getText().trim());
            objProv.setTelProv(TxtTelProv.getText().trim());
            objProv.setCorProv(TxtCorProv.getText().trim());
            objProv.setDescProv(TxtDescProv.getText().trim());
            objProv.Actualizar();

            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Eliminar() {
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar este proveedor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objProv.setNitProv(TxtNitProv.getText().trim());
            objProv.Eliminar();

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

        JlbTitGesProv = new javax.swing.JLabel();
        JlbNitProv = new javax.swing.JLabel();
        JlbNomProv = new javax.swing.JLabel();
        JlbCiuProv = new javax.swing.JLabel();
        JlbDirProv = new javax.swing.JLabel();
        JlbTelProv = new javax.swing.JLabel();
        JlbCorProv = new javax.swing.JLabel();
        JlbDescProv = new javax.swing.JLabel();
        TxtNitProv = new javax.swing.JTextField();
        TxtNomProv = new javax.swing.JTextField();
        TxtCiuProv = new javax.swing.JTextField();
        TxtDirProv = new javax.swing.JTextField();
        TxtTelProv = new javax.swing.JTextField();
        TxtCorProv = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtDescProv = new javax.swing.JTextArea();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();
        JlbNomProdProv = new javax.swing.JLabel();
        JlbIdProdProv = new javax.swing.JLabel();
        TxtNomProdProv = new javax.swing.JComboBox<>();
        TxtIdProdProv = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Proveedor");
        setResizable(false);

        JlbTitGesProv.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesProv.setText("Gestionar Proveedor");

        JlbNitProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNitProv.setText("Nit");

        JlbNomProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomProv.setText("Nombre");

        JlbCiuProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCiuProv.setText("Ciudad");

        JlbDirProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDirProv.setText("Dirección");

        JlbTelProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbTelProv.setText("Telefono");

        JlbCorProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCorProv.setText("Correo");

        JlbDescProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDescProv.setText("Descripción");

        TxtNitProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtNomProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtCiuProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtDirProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtTelProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtCorProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtCorProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCorProvActionPerformed(evt);
            }
        });

        TxtDescProv.setColumns(20);
        TxtDescProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtDescProv.setRows(5);
        jScrollPane1.setViewportView(TxtDescProv);

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

        JlbNomProdProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomProdProv.setText("Producto");

        JlbIdProdProv.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdProdProv.setText("Id Producto");

        TxtNomProdProv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));
        TxtNomProdProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNomProdProvActionPerformed(evt);
            }
        });

        TxtIdProdProv.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JlbDescProv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JlbCiuProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbNomProv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbDirProv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(TxtCiuProv, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TxtDirProv, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtNomProv, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(JlbIdProdProv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(JlbNitProv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtNitProv, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JlbNomProdProv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtIdProdProv, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(TxtNomProdProv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JlbTelProv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JlbCorProv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TxtTelProv, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                    .addComponent(TxtCorProv)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(118, 118, 118)
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
                                        .addGap(151, 151, 151)
                                        .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 32, Short.MAX_VALUE)))
                .addContainerGap(185, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JlbTitGesProv)
                .addGap(321, 321, 321))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(JlbTitGesProv)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNitProv)
                    .addComponent(TxtNitProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlbNomProdProv)
                    .addComponent(TxtNomProdProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomProv)
                    .addComponent(TxtNomProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlbIdProdProv)
                    .addComponent(TxtIdProdProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbCiuProv)
                    .addComponent(TxtCiuProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbDirProv)
                    .addComponent(TxtDirProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbTelProv)
                    .addComponent(TxtTelProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbCorProv)
                    .addComponent(TxtCorProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlbDescProv)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRegistrar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar))
                .addGap(26, 26, 26)
                .addComponent(BtnRegresar)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtCorProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCorProvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCorProvActionPerformed

    private void TxtNomProdProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNomProdProvActionPerformed
        this.BuscarIdProd();
    }//GEN-LAST:event_TxtNomProdProvActionPerformed

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
            java.util.logging.Logger.getLogger(FrmProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProveedor().setVisible(true);
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
    private javax.swing.JLabel JlbCiuProv;
    private javax.swing.JLabel JlbCorProv;
    private javax.swing.JLabel JlbDescProv;
    private javax.swing.JLabel JlbDirProv;
    private javax.swing.JLabel JlbIdProdProv;
    private javax.swing.JLabel JlbNitProv;
    private javax.swing.JLabel JlbNomProdProv;
    private javax.swing.JLabel JlbNomProv;
    private javax.swing.JLabel JlbTelProv;
    private javax.swing.JLabel JlbTitGesProv;
    private javax.swing.JTextField TxtCiuProv;
    private javax.swing.JTextField TxtCorProv;
    private javax.swing.JTextArea TxtDescProv;
    private javax.swing.JTextField TxtDirProv;
    private javax.swing.JTextField TxtIdProdProv;
    private javax.swing.JTextField TxtNitProv;
    private javax.swing.JComboBox<String> TxtNomProdProv;
    private javax.swing.JTextField TxtNomProv;
    private javax.swing.JTextField TxtTelProv;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
