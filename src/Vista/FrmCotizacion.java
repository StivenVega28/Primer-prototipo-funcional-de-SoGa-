package Vista;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsSesion;
import Modelo.ClsCotizacion;
import java.sql.SQLException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmCotizacion extends javax.swing.JFrame {

    public FrmCotizacion() {
        initComponents();
        if (!ClsSesion.esAdministrador()) {
            JOptionPane.showMessageDialog(null, "No tiene permisos para acceder a este módulo");
            this.dispose();
            return;
        }
        CrearTablaDetalleCot();
        Bloquear();
        BtnNuevo.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnRegresar.setEnabled(true);
    }

    private javax.swing.event.TableModelListener listenerTabla = null;
    ClsCotizacion objCot = new ClsCotizacion();
    boolean cargando = false;

    public void Bloquear() {
        TxtNumCot.setEnabled(false);
        CboNomCli.setEnabled(false);
        TxtNitCli.setEnabled(false);
        TxtDirCli.setEnabled(false);
        TxtSedCli.setEnabled(false);
        TxtTelCli.setEnabled(false);
        TxtCorCli.setEnabled(false);
        TxtFechCot.setEnabled(false);
        TxtSubTotal.setEnabled(false);
        TxtIva.setEnabled(false);
        TxtTotal.setEnabled(false);
        BtnNuevo.setEnabled(false);
        BtnGuardar.setEnabled(false);
        BtnBuscar.setEnabled(false);
        BtnActualizar.setEnabled(false);
        BtnRegresar.setEnabled(true);
        BtnEliminar.setEnabled(false);
        BtnEliminarFila.setEnabled(false);
        BtnAgregar.setEnabled(false);
    }

    public void Desbloquear() {
        TxtNumCot.setEnabled(false);
        CboNomCli.setEnabled(true);
        TxtNitCli.setEnabled(false);
        TxtDirCli.setEnabled(false);
        TxtSedCli.setEnabled(false);
        TxtTelCli.setEnabled(false);
        TxtCorCli.setEnabled(false);
        TxtFechCot.setEnabled(true);
        TxtSubTotal.setEnabled(false);
        TxtIva.setEnabled(false);
        TxtTotal.setEnabled(false);
        BtnNuevo.setEnabled(true);
        BtnGuardar.setEnabled(true);
        BtnBuscar.setEnabled(true);
        BtnActualizar.setEnabled(true);
        BtnRegresar.setEnabled(true);
        BtnEliminar.setEnabled(true);
        BtnEliminarFila.setEnabled(true);
        BtnAgregar.setEnabled(true);
    }

    public void Limpiar() {
        TxtNumCot.setText("");
        TxtNitCli.setText("");
        TxtDirCli.setText("");
        TxtSedCli.setText("");
        TxtTelCli.setText("");
        TxtCorCli.setText("");
        TxtSubTotal.setText("");
        TxtIva.setText("");
        TxtTotal.setText("");
        TxtFechCot.setDate(null);

        if (CboNomCli.getItemCount() > 0) {
            cargando = true;
            CboNomCli.setSelectedIndex(0);
            cargando = false;
        }

        CrearTablaDetalleCot();
    }

    public void llenarCboCli() {
        cargando = true;
        CboNomCli.removeAllItems();
        CboNomCli.addItem("Seleccione...");
        objCot.llenarCboCli();

        try {
            while (objCot.datosCli.next()) {
                CboNomCli.addItem(objCot.datosCli.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e);
        }

        cargando = false;
    }

    public void BuscarDatosCliente() {
        try {
            if (CboNomCli.getSelectedItem() != null
                    && !CboNomCli.getSelectedItem().toString().equals("Seleccione...")) {

                objCot.setNomCliBus(CboNomCli.getSelectedItem().toString());
                objCot.BuscarDatosCliente();

                if (objCot.datosCliente.next()) {
                    TxtNitCli.setText(objCot.datosCliente.getString(1));
                    TxtDirCli.setText(objCot.datosCliente.getString(2));
                    TxtSedCli.setText(objCot.datosCliente.getString(3));
                    TxtTelCli.setText(objCot.datosCliente.getString(4));
                    TxtCorCli.setText(objCot.datosCliente.getString(5));
                }
            } else {
                TxtNitCli.setText("");
                TxtDirCli.setText("");
                TxtSedCli.setText("");
                TxtTelCli.setText("");
                TxtCorCli.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + e);
        }
    }

    public void ObtenerSiguienteNumCot() {
        try {
            objCot.ObtenerSiguienteNumCot();
            if (objCot.datosCot.next()) {
                TxtNumCot.setText(objCot.datosCot.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener número de cotización: " + e);
        }
    }

    public void CrearTablaDetalleCot() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 3 && column != 8;
            }
        };

        modelo.addColumn("Item");
        modelo.addColumn("N° Inventario");
        modelo.addColumn("Medidas");
        modelo.addColumn("ID Producto");
        modelo.addColumn("Producto");
        modelo.addColumn("Especificaciones");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Valor Unitario");
        modelo.addColumn("Total");

        TblCot.setModel(modelo);
        CargarComboProductoEnTabla();
        EscucharCambiosTabla();
    }

    public void CargarComboProductoEnTabla() {
        JComboBox<String> cboProdTabla = new JComboBox<>();
        cboProdTabla.addItem("Seleccione...");

        objCot.llenarCboProd();
        try {
            while (objCot.datosProd.next()) {
                cboProdTabla.addItem(objCot.datosProd.getString(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos en tabla: " + e);
        }

        TblCot.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(cboProdTabla));
    }

    public void AgregarFilaCotizacion() {
        DefaultTableModel modelo = (DefaultTableModel) TblCot.getModel();
        int item = modelo.getRowCount() + 1;

        modelo.addRow(new Object[]{
            item, "", "", "", "Seleccione...", "", "", "", ""
        });
    }

    public void BuscarIdProductoFila(int fila) {
        try {
            Object producto = TblCot.getValueAt(fila, 4);

            if (producto != null && !producto.toString().equals("Seleccione...")) {
                objCot.setNomProdBus(producto.toString());
                objCot.BuscarIdProd();

                if (objCot.datosIdProd.next()) {
                    TblCot.setValueAt(objCot.datosIdProd.getString(1), fila, 3);
                }
            } else {
                TblCot.setValueAt("", fila, 3);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar ID producto: " + e);
        }
    }

    public void CalcularTotalFila(int fila) {
        try {
            Object cantObj = TblCot.getValueAt(fila, 6);
            Object valObj = TblCot.getValueAt(fila, 7);

            if (cantObj != null && valObj != null
                    && !cantObj.toString().trim().isEmpty()
                    && !valObj.toString().trim().isEmpty()) {

                double cantidad = Double.parseDouble(cantObj.toString());
                double valor = Double.parseDouble(valObj.toString());
                double total = cantidad * valor;

                TblCot.setValueAt(total, fila, 8);
            } else {
                TblCot.setValueAt("", fila, 8);
            }
        } catch (NumberFormatException e) {
            TblCot.setValueAt("", fila, 8);
        }
    }

    public void CalcularTotales() {
        double subtotal = 0;

        for (int i = 0; i < TblCot.getRowCount(); i++) {
            Object totalFila = TblCot.getValueAt(i, 8);
            if (totalFila != null && !totalFila.toString().trim().isEmpty()) {
                try {
                    subtotal += Double.parseDouble(totalFila.toString().replace(",", "."));
                } catch (NumberFormatException ex) {
                    // fila con total inválido, se omite
                }
            }
        }

        double iva = subtotal * 0.19;
        double total = subtotal + iva;

        TxtSubTotal.setText(String.format("%.2f", subtotal));
        TxtIva.setText(String.format("%.2f", iva));
        TxtTotal.setText(String.format("%.2f", total));
    }

    public void EscucharCambiosTabla() {
        // Remover listener anterior si existe
        if (listenerTabla != null) {
            TblCot.getModel().removeTableModelListener(listenerTabla);
        }

        listenerTabla = e -> {
            int fila = e.getFirstRow();
            int columna = e.getColumn();

            if (fila < 0 || columna < 0) {
                return;
            }

            if (columna == 4) {
                BuscarIdProductoFila(fila);
            }
            if (columna == 6 || columna == 7) {
                CalcularTotalFila(fila);
            }
            if (columna != 4) {
                CalcularTotales();
            }
        };

        TblCot.getModel().addTableModelListener(listenerTabla);
    }

    public void Guardar() {
        if (CboNomCli.getSelectedItem() == null
                || CboNomCli.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            CboNomCli.requestFocus();
            return;
        }
        if (TxtFechCot.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la fecha");
            TxtFechCot.requestFocus();
            return;
        }
        if (TxtNitCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró el NIT del cliente. Vuelva a seleccionarlo.");
            CboNomCli.requestFocus();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) TblCot.getModel();
        boolean hayDetalle = false;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object prod = TblCot.getValueAt(i, 4);
            if (prod != null && !prod.toString().equals("Seleccione...")
                    && !prod.toString().trim().isEmpty()) {
                hayDetalle = true;
                break;
            }
        }
        if (!hayDetalle) {
            JOptionPane.showMessageDialog(null, "Agregue al menos un producto");
            return;
        }

        try {
            java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");

            // Obtener el idCot desde el campo TxtNumCot (ya fue cargado por ObtenerSiguienteNumCot)
            int idCot = Integer.parseInt(TxtNumCot.getText().trim());

            objCot.setNomCliCot(CboNomCli.getSelectedItem().toString());
            objCot.setNitCliCot(TxtNitCli.getText().trim());
            objCot.setDirCliCot(TxtDirCli.getText().trim());
            objCot.setSedCliCot(TxtSedCli.getText().trim());
            objCot.setTelCliCot(TxtTelCli.getText().trim());
            objCot.setCorCliCot(TxtCorCli.getText().trim());
            objCot.setFechCot(formato.format(TxtFechCot.getDate()));

            float subtotal = TxtSubTotal.getText().trim().isEmpty() ? 0
                    : Float.parseFloat(TxtSubTotal.getText().trim().replace(",", "."));
            float iva = TxtIva.getText().trim().isEmpty() ? 0
                    : Float.parseFloat(TxtIva.getText().trim().replace(",", "."));
            float total = TxtTotal.getText().trim().isEmpty() ? 0
                    : Float.parseFloat(TxtTotal.getText().trim().replace(",", "."));

            for (int i = 0; i < modelo.getRowCount(); i++) {
                Object prod = TblCot.getValueAt(i, 4);
                Object idProdObj = TblCot.getValueAt(i, 3);
                Object invObj = TblCot.getValueAt(i, 1);
                Object medObj = TblCot.getValueAt(i, 2);
                Object especObj = TblCot.getValueAt(i, 5);
                Object cantObj = TblCot.getValueAt(i, 6);
                Object valObj = TblCot.getValueAt(i, 7);
                Object totObj = TblCot.getValueAt(i, 8);

                if (prod == null || prod.toString().equals("Seleccione...")
                        || prod.toString().trim().isEmpty()) {
                    continue;
                }

                if (idProdObj == null || idProdObj.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Fila " + (i + 1) + ": producto sin ID. Vuelva a seleccionarlo.");
                    return;
                }
                if (cantObj == null || cantObj.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite la cantidad en fila " + (i + 1));
                    return;
                }
                if (valObj == null || valObj.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite el valor unitario en fila " + (i + 1));
                    return;
                }

                objCot.setNumCot(idCot);
                objCot.setItemCot(i + 1);
                objCot.setNumInvCot(invObj != null ? invObj.toString() : "");
                objCot.setMedCot(medObj != null ? medObj.toString() : "");
                objCot.setIdInvCot(0);
                objCot.setIdProdCot(Integer.parseInt(idProdObj.toString().trim()));
                objCot.setProdCot(prod.toString());
                objCot.setEspCot(especObj != null ? especObj.toString() : "");
                objCot.setCantCot((int) Double.parseDouble(
                        cantObj.toString().trim().replace(",", ".")));
                objCot.setValUnitCot(Float.parseFloat(
                        valObj.toString().trim().replace(",", ".")));
                objCot.setValTotUnitCot(totObj != null && !totObj.toString().trim().isEmpty()
                        ? Float.parseFloat(totObj.toString().trim().replace(",", ".")) : 0);
                objCot.setSubTotCot(subtotal);
                objCot.setIvaCot(iva);
                objCot.setTotCot(total);

                objCot.Guardar();   // ahora Guardar() ya incluye idCot
            }

            JOptionPane.showMessageDialog(null, "Cotización guardada exitosamente");
            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnBuscar.setEnabled(true);
            BtnRegresar.setEnabled(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos numéricos incorrectos: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cotización: " + e);
        }
    }

    public void Buscar() {
        String buscar = JOptionPane.showInputDialog("Digite el número de cotización:");
        if (buscar == null || buscar.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite el número de cotización");
            return;
        }

        try {
            objCot.setNumCot(Integer.parseInt(buscar.trim()));

            // 1. Buscar encabezado (primera fila del idCot)
            objCot.Buscar();
            if (!objCot.datosCot.next()) {
                JOptionPane.showMessageDialog(null, "La cotización no existe");
                return;
            }

            // Cargar datos del encabezado en los campos
            TxtNumCot.setText(objCot.datosCot.getString("idCot"));
            String fechLeida = objCot.datosCot.getString("fechCot");
            String nomCli = objCot.datosCot.getString("nomCliCot");
            TxtNitCli.setText(objCot.datosCot.getString("nitCliCot"));
            TxtDirCli.setText(objCot.datosCot.getString("dirCliCot"));
            TxtSedCli.setText(objCot.datosCot.getString("sedCliCot"));
            TxtTelCli.setText(objCot.datosCot.getString("telCliCot"));
            TxtCorCli.setText(objCot.datosCot.getString("corCliCot"));

            // Cargar subtotal, iva, total del encabezado
            TxtSubTotal.setText(String.valueOf(objCot.datosCot.getFloat("subTotCot")));
            TxtIva.setText(String.valueOf(objCot.datosCot.getFloat("ivaCot")));
            TxtTotal.setText(String.valueOf(objCot.datosCot.getFloat("totCot")));

            // Cargar fecha
            try {
                java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyy-MM-dd");
                TxtFechCot.setDate(fmt.parse(fechLeida));
            } catch (java.text.ParseException ex) {
                TxtFechCot.setDate(null);
            }

            // Cargar combo de clientes y seleccionar el del registro
            this.llenarCboCli();
            cargando = true;
            CboNomCli.setSelectedItem(nomCli);
            cargando = false;

            // 2. Buscar filas de detalle — usa datosFilasCot (ResultSet separado)
            objCot.BuscarFilasPorId();
            CrearTablaDetalleCot();
            DefaultTableModel modelo = (DefaultTableModel) TblCot.getModel();

            while (objCot.datosFilasCot.next()) {
                modelo.addRow(new Object[]{
                    objCot.datosFilasCot.getInt("itemCot"),
                    objCot.datosFilasCot.getString("numInvCot"),
                    objCot.datosFilasCot.getString("medCot"),
                    objCot.datosFilasCot.getInt("idProdCot"),
                    objCot.datosFilasCot.getString("prodCot"),
                    objCot.datosFilasCot.getString("espCot"),
                    objCot.datosFilasCot.getDouble("cantCot"),
                    objCot.datosFilasCot.getDouble("valUnitCot"),
                    objCot.datosFilasCot.getDouble("valTotUnitCot")
                });
            }

            this.Desbloquear();
            BtnGuardar.setEnabled(false);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El número de cotización debe ser numérico");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cotización: " + e);
        }
    }

    public void Actualizar() {
        if (TxtNumCot.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busque una cotización para actualizar");
            return;
        }
        if (CboNomCli.getSelectedItem() == null
                || CboNomCli.getSelectedItem().toString().equals("Seleccione...")) {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente");
            CboNomCli.requestFocus();
            return;
        }
        if (TxtFechCot.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Seleccione la fecha");
            TxtFechCot.requestFocus();
            return;
        }
        if (TxtNitCli.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontró el NIT del cliente.");
            CboNomCli.requestFocus();
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) TblCot.getModel();
        boolean hayDetalle = false;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object prod = TblCot.getValueAt(i, 4);
            if (prod != null && !prod.toString().equals("Seleccione...")
                    && !prod.toString().trim().isEmpty()) {
                hayDetalle = true;
                break;
            }
        }
        if (!hayDetalle) {
            JOptionPane.showMessageDialog(null, "Agregue al menos un producto");
            return;
        }

        try {
            java.text.SimpleDateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
            int idCot = Integer.parseInt(TxtNumCot.getText().trim());

            float subtotal = TxtSubTotal.getText().trim().isEmpty() ? 0
                    : Float.parseFloat(TxtSubTotal.getText().trim().replace(",", "."));
            float iva = TxtIva.getText().trim().isEmpty() ? 0
                    : Float.parseFloat(TxtIva.getText().trim().replace(",", "."));
            float total = TxtTotal.getText().trim().isEmpty() ? 0
                    : Float.parseFloat(TxtTotal.getText().trim().replace(",", "."));

            // Borrar todas las filas del idCot
            objCot.setNumCot(idCot);
            objCot.EliminarPorId();

            // Re-insertar con el mismo idCot
            for (int i = 0; i < modelo.getRowCount(); i++) {
                Object prod = TblCot.getValueAt(i, 4);
                Object idProdObj = TblCot.getValueAt(i, 3);
                Object invObj = TblCot.getValueAt(i, 1);
                Object medObj = TblCot.getValueAt(i, 2);
                Object especObj = TblCot.getValueAt(i, 5);
                Object cantObj = TblCot.getValueAt(i, 6);
                Object valObj = TblCot.getValueAt(i, 7);
                Object totObj = TblCot.getValueAt(i, 8);

                if (prod == null || prod.toString().equals("Seleccione...")
                        || prod.toString().trim().isEmpty()) {
                    continue;
                }

                if (idProdObj == null || idProdObj.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Fila " + (i + 1) + ": producto sin ID. Vuelva a seleccionarlo.");
                    return;
                }
                if (cantObj == null || cantObj.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite la cantidad en fila " + (i + 1));
                    return;
                }
                if (valObj == null || valObj.toString().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite el valor unitario en fila " + (i + 1));
                    return;
                }

                objCot.setNumCot(idCot);
                objCot.setItemCot(i + 1);
                objCot.setNumInvCot(invObj != null ? invObj.toString() : "");
                objCot.setMedCot(medObj != null ? medObj.toString() : "");
                objCot.setIdInvCot(0);
                objCot.setIdProdCot(Integer.parseInt(idProdObj.toString().trim()));
                objCot.setProdCot(prod.toString());
                objCot.setEspCot(especObj != null ? especObj.toString() : "");
                objCot.setCantCot((int) Double.parseDouble(
                        cantObj.toString().trim().replace(",", ".")));
                objCot.setValUnitCot(Float.parseFloat(
                        valObj.toString().trim().replace(",", ".")));
                objCot.setValTotUnitCot(totObj != null && !totObj.toString().trim().isEmpty()
                        ? Float.parseFloat(totObj.toString().trim().replace(",", ".")) : 0);
                objCot.setNomCliCot(CboNomCli.getSelectedItem().toString());
                objCot.setNitCliCot(TxtNitCli.getText().trim());
                objCot.setDirCliCot(TxtDirCli.getText().trim());
                objCot.setSedCliCot(TxtSedCli.getText().trim());
                objCot.setTelCliCot(TxtTelCli.getText().trim());
                objCot.setCorCliCot(TxtCorCli.getText().trim());
                objCot.setFechCot(formato.format(TxtFechCot.getDate()));
                objCot.setSubTotCot(subtotal);
                objCot.setIvaCot(iva);
                objCot.setTotCot(total);

                objCot.Guardar();   // mismo Guardar(), ya incluye idCot
            }

            JOptionPane.showMessageDialog(null, "Cotización actualizada exitosamente");
            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnBuscar.setEnabled(true);
            BtnRegresar.setEnabled(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dato numérico incorrecto: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e);
        }
    }

    public void Regresar() {
        Vista.FrmIndex objFrmIndex = new Vista.FrmIndex();
        objFrmIndex.setVisible(true);
        this.dispose();
    }

    public void Nuevo() {
        this.Desbloquear();
        this.Limpiar();
        this.llenarCboCli();
        this.ObtenerSiguienteNumCot();
        BtnActualizar.setEnabled(false);
        BtnEliminar.setEnabled(false);
        BtnGuardar.setEnabled(true);
        CboNomCli.requestFocus();
    }

    public void Eliminar() {
        if (TxtNumCot.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busque una cotización para eliminar");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de eliminar la cotización N° " + TxtNumCot.getText().trim() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            objCot.setNumCot(Integer.parseInt(TxtNumCot.getText().trim()));
            objCot.EliminarPorId();

            JOptionPane.showMessageDialog(null, "Cotización eliminada exitosamente");
            this.Limpiar();
            this.Bloquear();
            BtnNuevo.setEnabled(true);
            BtnBuscar.setEnabled(true);
            BtnRegresar.setEnabled(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en el número de cotización: " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cotización: " + e);
        }
    }

    public void EliminarFilaCotizacion() {
        int filaSeleccionada = TblCot.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            return;
        }

        DefaultTableModel modelo = (DefaultTableModel) TblCot.getModel();
        modelo.removeRow(filaSeleccionada);

        // Renumerar los items después de eliminar
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.setValueAt(i + 1, i, 0);
        }

        CalcularTotales();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JlbTitCot = new javax.swing.JLabel();
        JlbSolGar = new javax.swing.JLabel();
        JlbNitSolGar = new javax.swing.JLabel();
        JlbTelSolGar = new javax.swing.JLabel();
        JlbCorSolGar = new javax.swing.JLabel();
        JlbDirSolGar = new javax.swing.JLabel();
        JlbNitSolGarTex = new javax.swing.JLabel();
        JlbTelSolGarTex = new javax.swing.JLabel();
        JlbCorSolGarTex = new javax.swing.JLabel();
        JlbDirSolGarTex = new javax.swing.JLabel();
        JlbNomCli = new javax.swing.JLabel();
        JlbNitCli = new javax.swing.JLabel();
        JlbDirCli = new javax.swing.JLabel();
        JlbSedCli = new javax.swing.JLabel();
        JlbTelCli = new javax.swing.JLabel();
        JlbCorCli = new javax.swing.JLabel();
        CboNomCli = new javax.swing.JComboBox<>();
        TxtNitCli = new javax.swing.JTextField();
        TxtDirCli = new javax.swing.JTextField();
        TxtSedCli = new javax.swing.JTextField();
        TxtTelCli = new javax.swing.JTextField();
        TxtCorCli = new javax.swing.JTextField();
        JlbNumCot = new javax.swing.JLabel();
        JlbFechCot = new javax.swing.JLabel();
        TxtNumCot = new javax.swing.JTextField();
        TxtFechCot = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        TblCot = new javax.swing.JTable();
        JlbSubTotal = new javax.swing.JLabel();
        JlbIva = new javax.swing.JLabel();
        JlbTotal = new javax.swing.JLabel();
        TxtSubTotal = new javax.swing.JTextField();
        TxtIva = new javax.swing.JTextField();
        TxtTotal = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        BtnBuscar = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnActualizar = new javax.swing.JButton();
        BtnRegresar = new javax.swing.JButton();
        BtnNuevo = new javax.swing.JButton();
        BtnEliminar = new javax.swing.JButton();
        BtnAgregar = new javax.swing.JButton();
        BtnEliminarFila = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestionar Cotizacion");
        setResizable(false);

        JlbTitCot.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        JlbTitCot.setText("Gestionar Cotización");

        JlbSolGar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbSolGar.setText("Soluciones Garantizadas PS S.A.S");

        JlbNitSolGar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNitSolGar.setText("NIT:");

        JlbTelSolGar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbTelSolGar.setText("Telefono:");

        JlbCorSolGar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCorSolGar.setText("Correo:");

        JlbDirSolGar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDirSolGar.setText("Direccion:");

        JlbNitSolGarTex.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        JlbNitSolGarTex.setText("901712788-9");

        JlbTelSolGarTex.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        JlbTelSolGarTex.setText("3014333665");

        JlbCorSolGarTex.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        JlbCorSolGarTex.setText("solucionesgarantizadasps@gmail.com");

        JlbDirSolGarTex.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        JlbDirSolGarTex.setText("Cra22#55-26");

        JlbNomCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNomCli.setText("Cliente:");

        JlbNitCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNitCli.setText("NIT: ");

        JlbDirCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbDirCli.setText("Dirección");

        JlbSedCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbSedCli.setText("Sede");

        JlbTelCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbTelCli.setText("Telefono");

        JlbCorCli.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbCorCli.setText("Correo");

        CboNomCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        CboNomCli.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione..." }));
        CboNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CboNomCliActionPerformed(evt);
            }
        });

        TxtNitCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtNitCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        TxtDirCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtDirCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        TxtDirCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtDirCliActionPerformed(evt);
            }
        });

        TxtSedCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtSedCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));
        TxtSedCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtSedCliActionPerformed(evt);
            }
        });

        TxtTelCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtTelCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        TxtCorCli.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtCorCli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        JlbNumCot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbNumCot.setText("Nº COTIZACION");

        JlbFechCot.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbFechCot.setText("Fecha");

        TxtNumCot.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtNumCot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        TblCot.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 255, 255), 1, true));
        TblCot.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TblCot.setForeground(new java.awt.Color(204, 255, 255));
        TblCot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Item", "Nº Inventario", "Medidas", "ID Producto", "Producto", "Especificaciones", "Cantidad", "Valor Unitario", "Total"
            }
        ));
        jScrollPane1.setViewportView(TblCot);

        JlbSubTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbSubTotal.setText("Sub Total");

        JlbIva.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbIva.setText("Iva 19%");

        JlbTotal.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        JlbTotal.setText("Total");

        TxtSubTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtSubTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        TxtIva.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtIva.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        TxtTotal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        TxtTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 255, 255)));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        BtnBuscar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BtnBuscar.setText("Buscar");
        BtnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBuscarActionPerformed(evt);
            }
        });

        BtnGuardar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BtnGuardar.setText("Guardar");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnActualizar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BtnActualizar.setText("Actializar");
        BtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActualizarActionPerformed(evt);
            }
        });

        BtnRegresar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BtnRegresar.setText("Regresar Index");
        BtnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegresarActionPerformed(evt);
            }
        });

        BtnNuevo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BtnNuevo.setText("Nuevo");
        BtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNuevoActionPerformed(evt);
            }
        });

        BtnEliminar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        BtnEliminar.setText("Eliminar");
        BtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(BtnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(BtnActualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(69, 69, 69))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(BtnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(BtnBuscar)
                .addGap(18, 18, 18)
                .addComponent(BtnNuevo)
                .addGap(18, 18, 18)
                .addComponent(BtnGuardar)
                .addGap(18, 18, 18)
                .addComponent(BtnActualizar)
                .addGap(18, 18, 18)
                .addComponent(BtnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(BtnRegresar)
                .addGap(82, 82, 82))
        );

        BtnAgregar.setText("Agregar");
        BtnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgregarActionPerformed(evt);
            }
        });

        BtnEliminarFila.setText("EliminarFila");
        BtnEliminarFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEliminarFilaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(JlbNitSolGar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(JlbTelSolGar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(JlbTelSolGarTex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(JlbNitSolGarTex, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(JlbDirSolGar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(JlbCorSolGar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(JlbCorSolGarTex, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(JlbDirSolGarTex, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(JlbSolGar, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(JlbCorCli, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(TxtCorCli, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(JlbSedCli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(JlbTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(TxtTelCli, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                        .addComponent(TxtSedCli))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(JlbNitCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbNomCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(JlbDirCli, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TxtNitCli, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(TxtDirCli)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(CboNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(69, 69, 69)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(JlbNumCot, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtNumCot))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(JlbFechCot, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(BtnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TxtFechCot, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                            .addComponent(BtnEliminarFila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(JlbSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(JlbIva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JlbTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TxtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtIva, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addGap(63, 63, 63)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
            .addGroup(layout.createSequentialGroup()
                .addGap(630, 630, 630)
                .addComponent(JlbTitCot, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(JlbTitCot)
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JlbSolGar)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JlbNitSolGar)
                                    .addComponent(JlbNitSolGarTex)
                                    .addComponent(JlbNomCli)
                                    .addComponent(CboNomCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JlbNumCot, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtNumCot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JlbFechCot, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtFechCot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbTelSolGarTex)
                            .addComponent(JlbTelSolGar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(JlbNitCli)
                            .addComponent(TxtNitCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbCorSolGar)
                            .addComponent(JlbCorSolGarTex)
                            .addComponent(JlbDirCli)
                            .addComponent(TxtDirCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbDirSolGar)
                            .addComponent(JlbDirSolGarTex)
                            .addComponent(JlbSedCli)
                            .addComponent(TxtSedCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtTelCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JlbCorCli, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCorCli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnAgregar)
                            .addComponent(BtnEliminarFila))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JlbSubTotal))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbIva)
                    .addComponent(TxtIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlbTotal)
                    .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 143, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtSedCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtSedCliActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSedCliActionPerformed

    private void TxtDirCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtDirCliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDirCliActionPerformed

    private void CboNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CboNomCliActionPerformed
        this.BuscarDatosCliente();
    }//GEN-LAST:event_CboNomCliActionPerformed

    private void BtnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgregarActionPerformed
        this.AgregarFilaCotizacion();
    }//GEN-LAST:event_BtnAgregarActionPerformed

    private void BtnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBuscarActionPerformed
        this.Buscar();
    }//GEN-LAST:event_BtnBuscarActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        this.Guardar();
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActualizarActionPerformed
        this.Actualizar();
    }//GEN-LAST:event_BtnActualizarActionPerformed

    private void BtnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegresarActionPerformed
        this.Regresar();
    }//GEN-LAST:event_BtnRegresarActionPerformed

    private void BtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNuevoActionPerformed
        this.Nuevo();
    }//GEN-LAST:event_BtnNuevoActionPerformed

    private void BtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarActionPerformed
        this.Eliminar();
    }//GEN-LAST:event_BtnEliminarActionPerformed

    private void BtnEliminarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEliminarFilaActionPerformed
        this.EliminarFilaCotizacion();
    }//GEN-LAST:event_BtnEliminarFilaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCotizacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCotizacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnActualizar;
    private javax.swing.JButton BtnAgregar;
    private javax.swing.JButton BtnBuscar;
    private javax.swing.JButton BtnEliminar;
    private javax.swing.JButton BtnEliminarFila;
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnNuevo;
    private javax.swing.JButton BtnRegresar;
    private javax.swing.JComboBox<String> CboNomCli;
    private javax.swing.JLabel JlbCorCli;
    private javax.swing.JLabel JlbCorSolGar;
    private javax.swing.JLabel JlbCorSolGarTex;
    private javax.swing.JLabel JlbDirCli;
    private javax.swing.JLabel JlbDirSolGar;
    private javax.swing.JLabel JlbDirSolGarTex;
    private javax.swing.JLabel JlbFechCot;
    private javax.swing.JLabel JlbIva;
    private javax.swing.JLabel JlbNitCli;
    private javax.swing.JLabel JlbNitSolGar;
    private javax.swing.JLabel JlbNitSolGarTex;
    private javax.swing.JLabel JlbNomCli;
    private javax.swing.JLabel JlbNumCot;
    private javax.swing.JLabel JlbSedCli;
    private javax.swing.JLabel JlbSolGar;
    private javax.swing.JLabel JlbSubTotal;
    private javax.swing.JLabel JlbTelCli;
    private javax.swing.JLabel JlbTelSolGar;
    private javax.swing.JLabel JlbTelSolGarTex;
    private javax.swing.JLabel JlbTitCot;
    private javax.swing.JLabel JlbTotal;
    private javax.swing.JTable TblCot;
    private javax.swing.JTextField TxtCorCli;
    private javax.swing.JTextField TxtDirCli;
    private com.toedter.calendar.JDateChooser TxtFechCot;
    private javax.swing.JTextField TxtIva;
    private javax.swing.JTextField TxtNitCli;
    private javax.swing.JTextField TxtNumCot;
    private javax.swing.JTextField TxtSedCli;
    private javax.swing.JTextField TxtSubTotal;
    private javax.swing.JTextField TxtTelCli;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
