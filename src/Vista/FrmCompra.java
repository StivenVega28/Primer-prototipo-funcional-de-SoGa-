package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsCompra;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class FrmCompra extends javax.swing.JFrame {

    public FrmCompra() {
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

    ClsCompra objCom = new ClsCompra();

    public void Bloquear() {
        TxtIdCom.setEnabled(false);
        TxtFechCom.setEnabled(false);
        CboNomProvCom.setEnabled(false);
        TxtNitProvCom.setEnabled(false);
        CboProdCom.setEnabled(false);
        TxtIdProdCom.setEnabled(false);
        TxtCanCom.setEnabled(false);
        TxtValCom.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtIdCom.setEnabled(false);
        TxtFechCom.setEnabled(true);
        CboNomProvCom.setEnabled(true);
        TxtNitProvCom.setEnabled(false);
        CboProdCom.setEnabled(true);
        TxtIdProdCom.setEnabled(false);
        TxtCanCom.setEnabled(true);
        TxtValCom.setEnabled(true);
        BtnRegistrar.setEnabled(true);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    public void Limpiar() {
        TxtIdCom.setText("");
        TxtFechCom.setDate(null);
        CboNomProvCom.setSelectedIndex(0);
        TxtNitProvCom.setText("");
        CboProdCom.setSelectedIndex(0);
        TxtIdProdCom.setText("");
        TxtCanCom.setText("");
        TxtValCom.setText("");
    }

    public void llenarCboProv() {
        CboNomProvCom.removeAllItems();
        CboNomProvCom.addItem("Seleccione...");
        objCom.llenarCboProv();
        try {
            while (objCom.datosProv.next()) {
                CboNomProvCom.addItem(objCom.datosProv.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores: " + e);
        }
    }

    public void BuscarDatosProv() {
        try {
            if (CboNomProvCom.getSelectedItem() != null
                    && !CboNomProvCom.getSelectedItem().toString().equals("Seleccione...")) {
                objCom.setNomProvBus(CboNomProvCom.getSelectedItem().toString());
                objCom.BuscarDatosProv();
                if (objCom.datosNitProv.next()) {
                    TxtNitProvCom.setText(objCom.datosNitProv.getString(1));
                }
            } else {
                TxtNitProvCom.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar datos del proveedor: " + e);
        }
    }

    public void llenarCboProd() {
        CboProdCom.removeAllItems();
        CboProdCom.addItem("Seleccione...");
        objCom.llenarCboProd();
        try {
            while (objCom.datosProd.next()) {
                CboProdCom.addItem(objCom.datosProd.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
    }

    public void BuscarIdProd() {
        try {
            if (CboProdCom.getSelectedItem() != null
                    && !CboProdCom.getSelectedItem().toString().equals("Seleccione...")) {
                objCom.setNomProdBus(CboProdCom.getSelectedItem().toString());
                objCom.BuscarIdProd();
                if (objCom.datosIdProd.next()) {
                    TxtIdProdCom.setText(objCom.datosIdProd.getString(1));
                }
            } else {
                TxtIdProdCom.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar id del producto: " + e);
        }
    }

    public boolean Validar() {
        if (CboNomProvCom.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el proveedor");
            CboNomProvCom.requestFocus();
            return false;
        } else if (CboProdCom.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el producto");
            CboProdCom.requestFocus();
            return false;
        } else if (TxtFechCom.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la fecha de la compra");
            TxtFechCom.requestFocus();
            return false;
        } else if (TxtCanCom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la cantidad");
            TxtCanCom.requestFocus();
            return false;
        } else if (TxtValCom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el valor de la compra");
            TxtValCom.requestFocus();
            return false;
        }
        return true;
    }

    public void Nuevo() {
        try {
            this.Desbloquear();
            this.Limpiar();
            this.llenarCboProv();
            this.llenarCboProd();
            objCom.Consecutivo();
            if (objCom.datosConsecutivo.next()) {
                TxtIdCom.setText(objCom.datosConsecutivo.getString(1));
            }
            BtnActualizar.setEnabled(false);
            BtnEliminar.setEnabled(false);
            CboNomProvCom.requestFocus();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar consecutivo: " + e);
        }
    }

    public void Registrar() {
        if (this.Validar()) {
            try {
                objCom.setNomProvBus(CboNomProvCom.getSelectedItem().toString());
                objCom.BuscarDatosProv();
                if (objCom.datosNitProv.next()) {
                    objCom.setNitProvCom(objCom.datosNitProv.getString(1));
                }
                objCom.setNomProdBus(CboProdCom.getSelectedItem().toString());
                objCom.BuscarIdProd();
                if (objCom.datosIdProd.next()) {
                    objCom.setIdProdCom(Integer.parseInt(objCom.datosIdProd.getString(1)));
                }
                objCom.setNomProvCom(CboNomProvCom.getSelectedItem().toString());
                objCom.setNomProdCom(CboProdCom.getSelectedItem().toString());
                objCom.setCanCom(Integer.parseInt(TxtCanCom.getText().trim()));
                java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
                objCom.setFechCom(formato.format(TxtFechCom.getDate()));
                String valLimpio = TxtValCom.getText().trim().replace(".", "").replace(",", ".");
                objCom.setValCom(Double.parseDouble(valLimpio));
                objCom.Registrar();
                objCom.RegistrarEnInventario();

                this.Limpiar();
                this.Bloquear();
                BtnNuevo.setEnabled(true);
                BtnRegresar.setEnabled(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener datos relacionados: " + e);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad y valor deben ser numéricos");
            }
        }
    }

    public void Buscar() {
        try {
            String busCom = JOptionPane.showInputDialog(
                    "Digite el ID de la compra que desea buscar");

            if (busCom == null || busCom.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Digite el ID de la compra");
            } else {
                objCom.setIdCom(Integer.parseInt(busCom.trim()));
                objCom.Buscar();

                if (objCom.datosCompra.next()) {
                    String idLeido = objCom.datosCompra.getString(1);
                    String nitProvLeido = objCom.datosCompra.getString(2);
                    String idProdLeido = objCom.datosCompra.getString(3);
                    String canLeido = objCom.datosCompra.getString(4);
                    String fechLeido = objCom.datosCompra.getString(5);
                    double valLeido = objCom.datosCompra.getDouble(6);
                    String nomProvLeido = objCom.datosCompra.getString(7);
                    String nomProdLeido = objCom.datosCompra.getString(8);

                    this.llenarCboProv();
                    this.llenarCboProd();

                    TxtIdCom.setText(idLeido);
                    try {
                        java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        TxtFechCom.setDate(formato.parse(fechLeido));
                    } catch (java.text.ParseException ex) {
                        TxtFechCom.setDate(null);
                    }
                    TxtNitProvCom.setText(nitProvLeido);
                    CboNomProvCom.setSelectedItem(nomProvLeido);
                    TxtIdProdCom.setText(idProdLeido);
                    CboProdCom.setSelectedItem(nomProdLeido);
                    TxtCanCom.setText(canLeido);
                    TxtValCom.setText(String.valueOf((long) valLeido));

                    this.Desbloquear();
                    TxtIdCom.setEnabled(false);
                    BtnRegistrar.setEnabled(false);
                    BtnNuevo.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "La compra no existe");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID de la compra debe ser numérico");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la compra: " + e);
        }
    }

    public void Actualizar() {
        if (this.Validar()) {
            try {
                objCom.setIdCom(Integer.parseInt(TxtIdCom.getText().trim()));
                objCom.setNomProvBus(CboNomProvCom.getSelectedItem().toString());
                objCom.BuscarDatosProv();
                if (objCom.datosNitProv.next()) {
                    objCom.setNitProvCom(objCom.datosNitProv.getString(1));
                }
                objCom.setNomProdBus(CboProdCom.getSelectedItem().toString());
                objCom.BuscarIdProd();
                if (objCom.datosIdProd.next()) {
                    objCom.setIdProdCom(Integer.parseInt(objCom.datosIdProd.getString(1)));
                }
                objCom.setNomProvCom(CboNomProvCom.getSelectedItem().toString());
                objCom.setNomProdCom(CboProdCom.getSelectedItem().toString());
                objCom.setCanCom(Integer.parseInt(TxtCanCom.getText().trim()));
                java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
                objCom.setFechCom(formato.format(TxtFechCom.getDate()));
                String valLimpio = TxtValCom.getText().trim().replace(".", "").replace(",", ".");
                objCom.setValCom(Double.parseDouble(valLimpio));
                objCom.Actualizar();

                this.Limpiar();
                this.Bloquear();
                BtnNuevo.setEnabled(true);
                BtnRegresar.setEnabled(true);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al obtener datos relacionados: " + e);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Cantidad y valor deben ser numéricos");
            }
        }
    }

    public void Eliminar() {
        if (TxtIdCom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busque una compra para eliminar");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar esta compra?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objCom.setIdCom(Integer.parseInt(TxtIdCom.getText().trim()));
            objCom.Eliminar();

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

        JlbTitGesCom = new javax.swing.JLabel();
        JlbIdCom = new javax.swing.JLabel();
        JlbNomProvCom = new javax.swing.JLabel();
        JlbNomProdCom = new javax.swing.JLabel();
        JlbCanCom = new javax.swing.JLabel();
        JlbValCom = new javax.swing.JLabel();
        TxtIdCom = new javax.swing.JTextField();
        CboNomProvCom = new javax.swing.JComboBox<>();
        CboProdCom = new javax.swing.JComboBox<>();
        TxtCanCom = new javax.swing.JTextField();
        TxtValCom = new javax.swing.JTextField();
        JlbFechCom = new javax.swing.JLabel();
        TxtFechCom = new com.toedter.calendar.JDateChooser();
        JlbNitProvCom = new javax.swing.JLabel();
        JlbIdProdCom = new javax.swing.JLabel();
        TxtNitProvCom = new javax.swing.JTextField();
        TxtIdProdCom = new javax.swing.JTextField();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Compra");
        setResizable(false);

        JlbTitGesCom.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbTitGesCom.setText("Gestionar Compra");

        JlbIdCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdCom.setText("Id Compra");

        JlbNomProvCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomProvCom.setText("Proveedor");

        JlbNomProdCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomProdCom.setText("Producto");

        JlbCanCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCanCom.setText("Cantidad");

        JlbValCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbValCom.setText("Valor");

        TxtIdCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        CboNomProvCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboNomProvCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));
        CboNomProvCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboNomProvComActionPerformed(evt);
            }
        });

        CboProdCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboProdCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));
        CboProdCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboProdComActionPerformed(evt);
            }
        });

        TxtCanCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtValCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        JlbFechCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbFechCom.setText("Fecha");

        JlbNitProvCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNitProvCom.setText("Nit Proveedor");

        JlbIdProdCom.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdProdCom.setText("Id Producto");

        TxtNitProvCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        TxtIdProdCom.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

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
                .addContainerGap(77, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JlbNomProdCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CboProdCom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(JlbValCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtValCom))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(JlbCanCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtCanCom))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(JlbNomProvCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(CboNomProvCom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(JlbIdCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtIdCom, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JlbFechCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtFechCom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JlbIdProdCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtIdProdCom, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JlbNitProvCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TxtNitProvCom, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(77, 77, 77))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(JlbTitGesCom)
                        .addGap(288, 288, 288))
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
                                .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(168, 168, 168))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(JlbTitGesCom)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbIdCom)
                            .addComponent(TxtIdCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlbFechCom))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbNomProvCom)
                            .addComponent(CboNomProvCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlbNitProvCom)
                            .addComponent(TxtNitProvCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(TxtFechCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomProdCom)
                    .addComponent(CboProdCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlbIdProdCom)
                    .addComponent(TxtIdProdCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbCanCom)
                    .addComponent(TxtCanCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbValCom)
                    .addComponent(TxtValCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnRegistrar)
                    .addComponent(BtnNuevo)
                    .addComponent(BtnBuscar)
                    .addComponent(BtnActualizar)
                    .addComponent(BtnEliminar))
                .addGap(18, 18, 18)
                .addComponent(BtnRegresar)
                .addGap(65, 65, 65))
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

    private void CboNomProvComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboNomProvComActionPerformed
        this.BuscarDatosProv();
    }//GEN-LAST:event_CboNomProvComActionPerformed

    private void CboProdComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboProdComActionPerformed
        this.BuscarIdProd();
    }//GEN-LAST:event_CboProdComActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCompra.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCompra.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCompra.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCompra.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCompra().setVisible(true);
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
    private javax.swing.JComboBox<String> CboNomProvCom;
    private javax.swing.JComboBox<String> CboProdCom;
    private javax.swing.JLabel JlbCanCom;
    private javax.swing.JLabel JlbFechCom;
    private javax.swing.JLabel JlbIdCom;
    private javax.swing.JLabel JlbIdProdCom;
    private javax.swing.JLabel JlbNitProvCom;
    private javax.swing.JLabel JlbNomProdCom;
    private javax.swing.JLabel JlbNomProvCom;
    private javax.swing.JLabel JlbTitGesCom;
    private javax.swing.JLabel JlbValCom;
    private javax.swing.JTextField TxtCanCom;
    private com.toedter.calendar.JDateChooser TxtFechCom;
    private javax.swing.JTextField TxtIdCom;
    private javax.swing.JTextField TxtIdProdCom;
    private javax.swing.JTextField TxtNitProvCom;
    private javax.swing.JTextField TxtValCom;
    // End of variables declaration//GEN-END:variables
}
