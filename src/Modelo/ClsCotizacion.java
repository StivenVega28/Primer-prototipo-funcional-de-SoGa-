package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsCotizacion {

    private int numCot;
    private String fechCot;
    private String nomCliBus;
    private String nitCliCot;
    private String nomCliCot;
    private String dirCliCot;
    private String sedCliCot;
    private String telCliCot;
    private String corCliCot;
    private String nomProdBus;

    private int idInvCot;
    private int idProdCot;
    private String medCot;
    private String prodCot;
    private String espCot;
    private int cantCot;
    private float valUnitCot;
    private float valTotUnitCot;
    private float subTotCot;
    private float ivaCot;
    private float totCot;
    private String numInvCot;
    private int itemCot;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosCli;
    public ResultSet datosCliente;
    public ResultSet datosProd;
    public ResultSet datosIdProd;
    public ResultSet datosCot;
    public ResultSet datosFilasCot;

    public void llenarCboCli() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomCli FROM cliente");
            datosCli = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e);
        }
    }

    public void BuscarDatosCliente() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "SELECT nitCli, dirCli, sedCli, telCli, corCli FROM cliente WHERE nomCli=?");
            objClsConec.Sql.setString(1, nomCliBus);
            datosCliente = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar datos del cliente: " + e);
        }
    }

    public void llenarCboProd() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomProd FROM producto");
            datosProd = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
    }

    public void BuscarIdProd() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT idProd FROM producto WHERE nomProd=?");
            objClsConec.Sql.setString(1, nomProdBus);
            datosIdProd = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar ID del producto: " + e);
        }
    }

//a este lo uso en nuevo, Generar el próximo número al crear una cotización nueva 
    public void ObtenerSiguienteNumCot() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "SELECT IFNULL(MAX(idCot), 0) + 1 FROM cotizacion");
            datosCot = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener número de cotización: " + e);
        }
    }

    public void Guardar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "INSERT INTO cotizacion (idCot, nitCliCot, idInvCot, idProdCot, nomCliCot, "
                    + "sedCliCot, dirCliCot, telCliCot, corCliCot, fechCot, medCot, "
                    + "prodCot, espCot, cantCot, valUnitCot, subTotCot, ivaCot, totCot, "
                    + "valTotUnitCot, numInvCot, itemCot) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            objClsConec.Sql.setInt(1, numCot);
            objClsConec.Sql.setString(2, nitCliCot);
            objClsConec.Sql.setNull(3, java.sql.Types.INTEGER);   // idInvCot siempre NULL
            objClsConec.Sql.setInt(4, idProdCot);
            objClsConec.Sql.setString(5, nomCliCot);
            objClsConec.Sql.setString(6, sedCliCot);
            objClsConec.Sql.setString(7, dirCliCot);
            objClsConec.Sql.setString(8, telCliCot);
            objClsConec.Sql.setString(9, corCliCot);
            objClsConec.Sql.setString(10, fechCot);
            objClsConec.Sql.setString(11, medCot);
            objClsConec.Sql.setString(12, prodCot);
            objClsConec.Sql.setString(13, espCot);
            objClsConec.Sql.setInt(14, cantCot);
            objClsConec.Sql.setFloat(15, valUnitCot);
            objClsConec.Sql.setFloat(16, subTotCot);
            objClsConec.Sql.setFloat(17, ivaCot);
            objClsConec.Sql.setFloat(18, totCot);
            objClsConec.Sql.setFloat(19, valTotUnitCot);
            objClsConec.Sql.setString(20, numInvCot);
            objClsConec.Sql.setInt(21, itemCot);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cotización: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "SELECT * FROM cotizacion WHERE idCot=?");
            objClsConec.Sql.setInt(1, numCot);
            datosCot = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar cotización: " + e);
        }
    }

    public void EliminarPorId() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "DELETE FROM cotizacion WHERE idCot=?");
            objClsConec.Sql.setInt(1, numCot);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cotización: " + e);
        }
    }

//esto busca todas las filas de la jtable
    public void BuscarFilasPorId() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "SELECT itemCot, numInvCot, medCot, idProdCot, prodCot, "
                    + "espCot, cantCot, valUnitCot, valTotUnitCot "
                    + "FROM cotizacion WHERE idCot=?");
            objClsConec.Sql.setInt(1, numCot);
            datosFilasCot = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar filas: " + e);
        }
    }

//a este lo llamo en guardar Obtener el ID que se acaba de guardar para usarlo en otra operación inmediatamente después
    public void ObtenerUltimoIdCot() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "SELECT MAX(idCot) FROM cotizacion");
            datosCot = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener último ID: " + e);
        }
    }

//Encapsulamiento
    public int getNumCot() {
        return numCot;
    }

    public void setNumCot(int numCot) {
        this.numCot = numCot;
    }

    public String getFechCot() {
        return fechCot;
    }

    public void setFechCot(String fechCot) {
        this.fechCot = fechCot;
    }

    public String getNomCliBus() {
        return nomCliBus;
    }

    public void setNomCliBus(String nomCliBus) {
        this.nomCliBus = nomCliBus;
    }

    public String getNitCliCot() {
        return nitCliCot;
    }

    public void setNitCliCot(String nitCliCot) {
        this.nitCliCot = nitCliCot;
    }

    public String getNomCliCot() {
        return nomCliCot;
    }

    public void setNomCliCot(String nomCliCot) {
        this.nomCliCot = nomCliCot;
    }

    public String getDirCliCot() {
        return dirCliCot;
    }

    public void setDirCliCot(String dirCliCot) {
        this.dirCliCot = dirCliCot;
    }

    public String getSedCliCot() {
        return sedCliCot;
    }

    public void setSedCliCot(String sedCliCot) {
        this.sedCliCot = sedCliCot;
    }

    public String getTelCliCot() {
        return telCliCot;
    }

    public void setTelCliCot(String telCliCot) {
        this.telCliCot = telCliCot;
    }

    public String getCorCliCot() {
        return corCliCot;
    }

    public void setCorCliCot(String corCliCot) {
        this.corCliCot = corCliCot;
    }

    public String getNomProdBus() {
        return nomProdBus;
    }

    public void setNomProdBus(String nomProdBus) {
        this.nomProdBus = nomProdBus;
    }

    public int getIdInvCot() {
        return idInvCot;
    }

    public void setIdInvCot(int idInvCot) {
        this.idInvCot = idInvCot;
    }

    public int getIdProdCot() {
        return idProdCot;
    }

    public void setIdProdCot(int idProdCot) {
        this.idProdCot = idProdCot;
    }

    public String getMedCot() {
        return medCot;
    }

    public void setMedCot(String medCot) {
        this.medCot = medCot;
    }

    public String getProdCot() {
        return prodCot;
    }

    public void setProdCot(String prodCot) {
        this.prodCot = prodCot;
    }

    public String getEspCot() {
        return espCot;
    }

    public void setEspCot(String espCot) {
        this.espCot = espCot;
    }

    public int getCantCot() {
        return cantCot;
    }

    public void setCantCot(int cantCot) {
        this.cantCot = cantCot;
    }

    public float getValUnitCot() {
        return valUnitCot;
    }

    public void setValUnitCot(float valUnitCot) {
        this.valUnitCot = valUnitCot;
    }

    public float getValTotUnitCot() {
        return valTotUnitCot;
    }

    public void setValTotUnitCot(float valTotUnitCot) {
        this.valTotUnitCot = valTotUnitCot;
    }

    public float getSubTotCot() {
        return subTotCot;
    }

    public void setSubTotCot(float subTotCot) {
        this.subTotCot = subTotCot;
    }

    public float getIvaCot() {
        return ivaCot;
    }

    public void setIvaCot(float ivaCot) {
        this.ivaCot = ivaCot;
    }

    public float getTotCot() {
        return totCot;
    }

    public void setTotCot(float totCot) {
        this.totCot = totCot;
    }

    public String getNumInvCot() {
        return numInvCot;
    }

    public void setNumInvCot(String numInvCot) {
        this.numInvCot = numInvCot;
    }

    public int getItemCot() {
        return itemCot;
    }

    public void setItemCot(int itemCot) {
        this.itemCot = itemCot;
    }

    public ClsConexion getObjClsConec() {
        return objClsConec;
    }

    public void setObjClsConec(ClsConexion objClsConec) {
        this.objClsConec = objClsConec;
    }

}
