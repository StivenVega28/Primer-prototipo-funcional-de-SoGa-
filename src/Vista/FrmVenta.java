package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsVenta;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmVenta extends javax.swing.JFrame {

    public FrmVenta() {
        initComponents();
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para acceder a este módulo");
            this.dispose();
            return;
        }
        this.CrearTablaDetalle();
        this.Bloquear();
        BtnNuevo.setEnabled(true);
        BtnRegresar.setEnabled(true);

        TblLisVent.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SeleccionarFila();
            }
        });
    }

    ClsVenta objVent = new ClsVenta();
    boolean cargando = false;
    int filaSeleccionada = -1;

    public void MostrarSiguienteId() {
        try {
            objVent.ObtenerSiguienteId();
            if (objVent.datosVenta.next()) {
                TxtIdVent.setText(objVent.datosVenta.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID: " + e);
        }
    }

    public void CrearTablaDetalle() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Id Inv");
        modelo.addColumn("Id Producto");
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor");
        TblLisVent.setModel(modelo);
        TblLisVent.getColumnModel().getColumn(0).setMinWidth(0);
        TblLisVent.getColumnModel().getColumn(0).setMaxWidth(0);
        TblLisVent.getColumnModel().getColumn(0).setWidth(0);
    }

    public void Bloquear() {
        TxtIdVent.setEnabled(false);
        CboProdVent.setEnabled(false);
        TxtIdProdVent.setEnabled(false);
        TxtFechVent.setEnabled(false);
        CboNomCliVent.setEnabled(false);
        TxtValVent.setEnabled(false);
        TxtCanVent.setEnabled(false);
        TxtDescVent.setEnabled(false);
        BtnAgregar.setEnabled(false);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnRegresar.setEnabled(false);
        BtnQuitar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtIdVent.setEnabled(false);
        CboProdVent.setEnabled(true);
        TxtIdProdVent.setEnabled(false);
        TxtFechVent.setEnabled(true);
        CboNomCliVent.setEnabled(true);
        TxtValVent.setEnabled(true);
        TxtCanVent.setEnabled(true);
        TxtDescVent.setEnabled(true);
        BtnAgregar.setEnabled(true);
        BtnRegistrar.setEnabled(false);
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnRegresar.setEnabled(true);
        BtnQuitar.setEnabled(true);
    }

    public void Limpiar() {
        filaSeleccionada = -1;
        BtnAgregar.setText("Agregar");
        TxtIdVent.setText("");
        TxtIdProdVent.setText("");
        TxtValVent.setText("");
        TxtCanVent.setText("");
        TxtDescVent.setText("");
        TxtFechVent.setDate(null);
        if (CboProdVent.getItemCount() > 0) {
            cargando = true;
            CboProdVent.setSelectedIndex(0);
            cargando = false;
        }
        if (CboNomCliVent.getItemCount() > 0) {
            cargando = true;
            CboNomCliVent.setSelectedIndex(0);
            cargando = false;
        }
        CrearTablaDetalle();
    }

    public void llenarCboProd() {
        cargando = true;
        CboProdVent.removeAllItems();
        CboProdVent.addItem("Seleccione...");
        objVent.llenarCboProd();
        try {
            while (objVent.datosProd.next()) {
                CboProdVent.addItem(objVent.datosProd.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
        cargando = false;
    }

    public void BuscarIdProd() {
        try {
            if (CboProdVent.getSelectedItem() != null
                    && !CboProdVent.getSelectedItem().toString().equals("Seleccione...")) {

                objVent.setNomProdBus(CboProdVent.getSelectedItem().toString());
                objVent.BuscarIdProd();

                if (objVent.datosIdProd.next()) {
                    String idProd = objVent.datosIdProd.getString(1);
                    TxtIdProdVent.setText(idProd);

                    // Verificar stock disponible
                    objVent.setIdProdVent(Integer.parseInt(idProd));
                    objVent.BuscarStockInventario();

                    if (objVent.datosStockInv.next()) {
                        TxtCanVent.setToolTipText("Stock disponible: "
                                + objVent.datosStockInv.getInt(2) + " unidades");
                    } else {
                        TxtIdProdVent.setText("");
                        JOptionPane.showMessageDialog(null,
                                "Este producto no tiene stock en inventario");
                        cargando = true;
                        CboProdVent.setSelectedIndex(0);
                        cargando = false;
                    }
                }
            } else {
                TxtIdProdVent.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar producto: " + e);
        }
    }

    public void llenarCboCli() {
        cargando = true;
        CboNomCliVent.removeAllItems();
        CboNomCliVent.addItem("Seleccione...");
        objVent.llenarCboCli();
        try {
            while (objVent.datosCli.next()) {
                CboNomCliVent.addItem(objVent.datosCli.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e);
        }
        cargando = false;
    }

    public void AgregarProducto() {
        if (CboProdVent.getSelectedItem() == null
                || CboProdVent.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el producto");
            CboProdVent.requestFocus();
            return;
        }
        if (TxtCanVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la cantidad");
            TxtCanVent.requestFocus();
            return;
        }
        if (TxtValVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el valor de venta");
            TxtValVent.requestFocus();
            return;
        }

        try {
            String nomProd = CboProdVent.getSelectedItem().toString();
            int idProd = Integer.parseInt(TxtIdProdVent.getText().trim());
            int cantidad = Integer.parseInt(TxtCanVent.getText().trim());
            String valLimpio = TxtValVent.getText().trim().replace(".", "").replace(",", ".");
            double valor = Double.parseDouble(valLimpio);

            // Verificar stock
            objVent.setIdProdVent(idProd);
            objVent.BuscarStockInventario();

            if (objVent.datosStockInv.next()) {
                int idInv = objVent.datosStockInv.getInt(1);
                int stock = objVent.datosStockInv.getInt(2);

                if (cantidad > stock) {
                    JOptionPane.showMessageDialog(null,
                            "Stock insuficiente. Disponible: " + stock + " unidades");
                    return;
                }

                DefaultTableModel modelo = (DefaultTableModel) TblLisVent.getModel();

                if (filaSeleccionada >= 0) {
                    //actualizar la fila seleccionada
                    modelo.setValueAt(idInv, filaSeleccionada, 0);
                    modelo.setValueAt(idProd, filaSeleccionada, 1);
                    modelo.setValueAt(nomProd, filaSeleccionada, 2);
                    modelo.setValueAt(cantidad, filaSeleccionada, 3);
                    modelo.setValueAt(valor, filaSeleccionada, 4);

                    filaSeleccionada = -1;          // resetear modo
                    BtnAgregar.setText("Agregar");  // restaurar texto del botón
                } else {
                    modelo.addRow(new Object[]{
                        idInv, idProd, nomProd, cantidad, valor
                    });
                }

                // Limpiar campos del producto
                cargando = true;
                CboProdVent.setSelectedIndex(0);
                cargando = false;
                TxtIdProdVent.setText("");
                TxtCanVent.setText("");
                TxtValVent.setText("");

                BtnRegistrar.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "El producto no tiene stock en inventario");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad y valor deben ser numéricos");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar stock: " + e);
        }
    }

    public void Registrar() {
        DefaultTableModel modelo = (DefaultTableModel) TblLisVent.getModel();

        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Agregue al menos un producto");
            return;
        }
        if (TxtFechVent.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la fecha de venta");
            TxtFechVent.requestFocus();
            return;
        }
        if (CboNomCliVent.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el cliente");
            CboNomCliVent.requestFocus();
            return;
        }
        if (TxtDescVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la descripción de la venta");
            TxtDescVent.requestFocus();
            return;
        }

        try {
            java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String fecha = formato.format(TxtFechVent.getDate());

            objVent.setNomCliBus(CboNomCliVent.getSelectedItem().toString());
            objVent.BuscarNitCli();
            String nitCli = "";
            if (objVent.datosNitCli.next()) {
                nitCli = objVent.datosNitCli.getString(1);
            }
            String nomCli = CboNomCliVent.getSelectedItem().toString();
            String desc = TxtDescVent.getText().trim();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int idInv = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                int idProd = Integer.parseInt(modelo.getValueAt(i, 1).toString());
                String nomProd = modelo.getValueAt(i, 2).toString();
                int cantidad = Integer.parseInt(modelo.getValueAt(i, 3).toString());
                double valor = Double.parseDouble(modelo.getValueAt(i, 4).toString());

                objVent.setIdInvVent(idInv);
                objVent.setIdProdVent(idProd);
                objVent.setNomProdVent(nomProd);
                objVent.setNitCliVent(nitCli);
                objVent.setFechVent(fecha);
                objVent.setNomCliVent(nomCli);
                objVent.setCanVent(cantidad);
                objVent.setValVent(valor);
                objVent.setDescVent(desc);

                objVent.Registrar();
                objVent.DescontarInventario();
            }

            JOptionPane.showMessageDialog(null, "Venta registrada exitosamente");
            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + e);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en los datos de la tabla");
        }
    }

    public void Buscar() {
        String busVent = JOptionPane.showInputDialog(
                "Digite el ID de la venta que desea buscar");

        if (busVent == null || busVent.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el ID de la venta");
            return;
        }

        try {
            objVent.setIdVent(Integer.parseInt(busVent.trim()));
            objVent.Buscar();

            if (!objVent.datosVenta.next()) {
                JOptionPane.showMessageDialog(null, "La venta no existe");
                return;
            }
            String idVentLeido = objVent.datosVenta.getString(1);
            String idProdLeido = objVent.datosVenta.getString(3);
            String nitCliLeido = objVent.datosVenta.getString(4);
            String fechLeido = objVent.datosVenta.getString(5);
            String nomCliLeido = objVent.datosVenta.getString(6);
            String canLeido = objVent.datosVenta.getString(7);
            double valLeido = objVent.datosVenta.getDouble(8);
            String descLeido = objVent.datosVenta.getString(9);
            String nomProdLeido = objVent.datosVenta.getString(10);

            this.llenarCboProd();
            this.llenarCboCli();

            TxtIdVent.setText(idVentLeido);
            TxtIdProdVent.setText(idProdLeido);
            CboProdVent.setSelectedItem(nomProdLeido);
            try {
                java.text.SimpleDateFormat formato
                        = new java.text.SimpleDateFormat("yyyy-MM-dd");
                TxtFechVent.setDate(formato.parse(fechLeido));
            } catch (java.text.ParseException ex) {
                TxtFechVent.setDate(null);
            }
            CboNomCliVent.setSelectedItem(nomCliLeido);
            TxtValVent.setText(String.valueOf((long) valLeido));
            TxtCanVent.setText(canLeido);
            TxtDescVent.setText(descLeido);

            objVent.setNomCliVent(nomCliLeido);
            objVent.setFechVent(fechLeido);
            objVent.BuscarPorClienteFecha();

            CrearTablaDetalle();
            DefaultTableModel modelo = (DefaultTableModel) TblLisVent.getModel();

            while (objVent.datosVenta.next()) {
                int idInv = objVent.datosVenta.getInt(10);
                int idProd = objVent.datosVenta.getInt(2);
                String nomProd = objVent.datosVenta.getString(3);
                int cantidad = objVent.datosVenta.getInt(4);
                double valor = objVent.datosVenta.getDouble(5);

                modelo.addRow(new Object[]{
                    idInv,
                    idProd,
                    nomProd,
                    cantidad,
                    valor
                });
            }

            this.Desbloquear();
            BtnRegistrar.setEnabled(false);
            BtnNuevo.setEnabled(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID de la venta debe ser numérico");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la venta: " + e);
        }
    }

    public void Actualizar() {
        if (TxtIdVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busque una venta para actualizar");
            return;
        }
        if (CboProdVent.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el producto");
            CboProdVent.requestFocus();
            return;
        }
        if (TxtCanVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la cantidad");
            TxtCanVent.requestFocus();
            return;
        }
        if (TxtValVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el valor de venta");
            TxtValVent.requestFocus();
            return;
        }
        if (TxtFechVent.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la fecha de venta");
            TxtFechVent.requestFocus();
            return;
        }
        if (CboNomCliVent.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione el cliente");
            CboNomCliVent.requestFocus();
            return;
        }
        if (TxtDescVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite la descripción");
            TxtDescVent.requestFocus();
            return;
        }

        try {
            // Buscar idInv y verificar stock
            int idProd = Integer.parseInt(TxtIdProdVent.getText().trim());
            objVent.setIdProdVent(idProd);
            objVent.BuscarStockInventario();

            if (objVent.datosStockInv.next()) {
                int idInv = objVent.datosStockInv.getInt(1);

                // Buscar NIT del cliente
                objVent.setNomCliBus(CboNomCliVent.getSelectedItem().toString());
                objVent.BuscarNitCli();
                String nitCli = "";
                if (objVent.datosNitCli.next()) {
                    nitCli = objVent.datosNitCli.getString(1);
                }

                java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
                String valLimpio = TxtValVent.getText().trim().replace(".", "").replace(",", ".");

                if (TxtIdVent.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Primero busque una venta para actualizar");
                    return;
                }
                objVent.setIdVent(Integer.parseInt(TxtIdVent.getText().trim()));
                objVent.setIdInvVent(idInv);
                objVent.setIdProdVent(idProd);
                objVent.setNomProdVent(CboProdVent.getSelectedItem().toString());
                objVent.setNitCliVent(nitCli);
                objVent.setFechVent(formato.format(TxtFechVent.getDate()));
                objVent.setNomCliVent(CboNomCliVent.getSelectedItem().toString());
                objVent.setCanVent(Integer.parseInt(TxtCanVent.getText().trim()));
                objVent.setValVent(Double.parseDouble(valLimpio));
                objVent.setDescVent(TxtDescVent.getText().trim());
                objVent.Actualizar();

                this.Limpiar();
                this.Bloquear();
                BtnNuevo.setEnabled(true);
                BtnRegresar.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "El producto no tiene stock en inventario");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos numéricos incorrectos");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e);
        }
    }

    public void Eliminar() {
        if (TxtIdVent.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busque una venta para eliminar");
            return;
        }
        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea eliminar esta venta?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            objVent.setIdVent(Integer.parseInt(TxtIdVent.getText().trim()));
            objVent.Eliminar();
            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnRegresar.setEnabled(true);
        }
    }

    public void Nuevo() {
        this.Desbloquear();
        this.Limpiar();
        this.llenarCboProd();
        this.llenarCboCli();
        this.MostrarSiguienteId();
        BtnRegistrar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        CboProdVent.requestFocus();
    }

    public void Regresar() {
        FrmIndex objFrmIndex = new FrmIndex();
        objFrmIndex.setVisible(true);
        this.dispose();
    }

    public void SeleccionarFila() {
        int fila = TblLisVent.getSelectedRow();

        if (fila >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) TblLisVent.getModel();
            filaSeleccionada = fila;

            String idProd = modelo.getValueAt(fila, 1).toString();
            String nomProd = modelo.getValueAt(fila, 2).toString();
            String cant = modelo.getValueAt(fila, 3).toString();
            String valor = modelo.getValueAt(fila, 4).toString();

            TxtIdProdVent.setEnabled(true);
            TxtCanVent.setEnabled(true);
            TxtValVent.setEnabled(true);
            CboProdVent.setEnabled(true);

            cargando = true;
            CboProdVent.setSelectedItem(nomProd);
            cargando = false;

            TxtIdProdVent.setText(idProd);
            TxtCanVent.setText(cant);
            TxtValVent.setText(String.valueOf((long) Double.parseDouble(valor)));

            TxtIdProdVent.setEnabled(false);

            BtnAgregar.setText("Actualizar");
        }
    }

    public void QuitarFila() {
        DefaultTableModel modelo = (DefaultTableModel) TblLisVent.getModel();
        int fila = TblLisVent.getSelectedRow();

        if (fila >= 0) {
            modelo.removeRow(fila);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para quitar");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlbVenta = new javax.swing.JLabel();
        JlbIdProdVent = new javax.swing.JLabel();
        JlbFechVent = new javax.swing.JLabel();
        JlbNomCliVent = new javax.swing.JLabel();
        JlbValVent = new javax.swing.JLabel();
        JlbCantVent = new javax.swing.JLabel();
        JlbDescVent = new javax.swing.JLabel();
        TxtIdProdVent = new javax.swing.JTextField();
        TxtFechVent = new com.toedter.calendar.JDateChooser();
        CboNomCliVent = new javax.swing.JComboBox<>();
        TxtValVent = new javax.swing.JTextField();
        TxtCanVent = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TxtDescVent = new javax.swing.JTextArea();
        BtnRegistrar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnBuscar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();
        JlbProdVent = new javax.swing.JLabel();
        CboProdVent = new javax.swing.JComboBox<>();
        BtnAgregar = new javax.swing.JButton();
        JlbIdVent = new javax.swing.JLabel();
        TxtIdVent = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        TblLisVent = new javax.swing.JTable();
        BtnQuitar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Venta");
        setResizable(false);

        JlbVenta.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        JlbVenta.setText("Gestionar Venta");
        JlbVenta.setToolTipText("Venta");

        JlbIdProdVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdProdVent.setText("ID Producto");

        JlbFechVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbFechVent.setText("Fecha De Venta:");

        JlbNomCliVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomCliVent.setText("Nombre De Cliente:");

        JlbValVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbValVent.setText("Valor Venta:");

        JlbCantVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCantVent.setText("Cantidad:");

        JlbDescVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDescVent.setText("Descripcion Venta:");

        TxtIdProdVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdProdVentActionPerformed(evt);
            }
        });

        CboNomCliVent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));

        TxtValVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtValVentActionPerformed(evt);
            }
        });

        TxtCanVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCanVentActionPerformed(evt);
            }
        });

        TxtDescVent.setColumns(20);
        TxtDescVent.setRows(5);
        jScrollPane1.setViewportView(TxtDescVent);

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

        JlbProdVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbProdVent.setText("Producto");

        CboProdVent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));
        CboProdVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboProdVentActionPerformed(evt);
            }
        });

        BtnAgregar.setText("Agregar");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        JlbIdVent.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIdVent.setText("Id Venta");

        TxtIdVent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdVentActionPerformed(evt);
            }
        });

        TblLisVent.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TblLisVent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Id Producto", "Cantidad"
            }
        ));
        jScrollPane2.setViewportView(TblLisVent);

        BtnQuitar.setText("Quitar");
        BtnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnQuitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                .addComponent(JlbDescVent, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JlbIdProdVent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbFechVent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbNomCliVent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbValVent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbCantVent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(JlbProdVent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TxtIdProdVent)
                                    .addComponent(TxtFechVent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(CboNomCliVent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(TxtValVent)
                                    .addComponent(TxtCanVent, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(CboProdVent, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(JlbIdVent, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TxtIdVent, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 73, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(BtnQuitar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(108, 108, 108))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(JlbVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(515, 515, 515))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(JlbVenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbProdVent)
                            .addComponent(CboProdVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlbIdVent)
                            .addComponent(TxtIdVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbIdProdVent)
                            .addComponent(TxtIdProdVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(JlbFechVent))
                    .addComponent(TxtFechVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbNomCliVent)
                    .addComponent(CboNomCliVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbValVent)
                    .addComponent(TxtValVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtCanVent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlbCantVent)
                    .addComponent(BtnAgregar)
                    .addComponent(BtnQuitar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JlbDescVent)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
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

    private void TxtIdProdVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdProdVentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdProdVentActionPerformed

    private void TxtValVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtValVentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtValVentActionPerformed

    private void TxtCanVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCanVentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtCanVentActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        this.Nuevo();
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        this.AgregarProducto();
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistrarActionPerformed
        this.Registrar();
    }//GEN-LAST:event_BtnRegistrarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        this.Buscar();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        this.Eliminar();
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegresarActionPerformed
        this.Regresar();
    }//GEN-LAST:event_BtnRegresarActionPerformed

    private void CboProdVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboProdVentActionPerformed
        if (!cargando)
            this.BuscarIdProd();
    }//GEN-LAST:event_CboProdVentActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        this.Actualizar();
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void TxtIdVentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdVentActionPerformed
        this.SeleccionarFila();
    }//GEN-LAST:event_TxtIdVentActionPerformed

    private void BtnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnQuitarActionPerformed
        this.QuitarFila();
    }//GEN-LAST:event_BtnQuitarActionPerformed

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
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmVenta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnQuitar;
    private javax.swing.JButton BtnRegistrar;
    private javax.swing.JButton BtnRegresar;
    private javax.swing.JComboBox<String> CboNomCliVent;
    private javax.swing.JComboBox<String> CboProdVent;
    private javax.swing.JLabel JlbCantVent;
    private javax.swing.JLabel JlbDescVent;
    private javax.swing.JLabel JlbFechVent;
    private javax.swing.JLabel JlbIdProdVent;
    private javax.swing.JLabel JlbIdVent;
    private javax.swing.JLabel JlbNomCliVent;
    private javax.swing.JLabel JlbProdVent;
    private javax.swing.JLabel JlbValVent;
    private javax.swing.JLabel JlbVenta;
    private javax.swing.JTable TblLisVent;
    private javax.swing.JTextField TxtCanVent;
    private javax.swing.JTextArea TxtDescVent;
    private com.toedter.calendar.JDateChooser TxtFechVent;
    private javax.swing.JTextField TxtIdProdVent;
    private javax.swing.JTextField TxtIdVent;
    private javax.swing.JTextField TxtValVent;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
