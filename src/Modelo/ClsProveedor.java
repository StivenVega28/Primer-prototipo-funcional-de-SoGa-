package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsProveedor {

    private String nitProv;
    private int idProdProv;
    private String nomProdProv;
    private String nomProv;
    private String ciuProv;
    private String dirProv;
    private String telProv;
    private String corProv;
    private String descProv;
    private String nomProdBus;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosProveedor;
    public ResultSet datosProd;
    public ResultSet datosIdProd;

    public void llenarCboProd() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomProd FROM producto");
            objClsConec.Sql.executeQuery();
            datosProd = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
    }

    public void BuscarIdProd() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT idProd FROM producto WHERE nomProd=?");
            objClsConec.Sql.setString(1, nomProdBus);
            objClsConec.Sql.executeQuery();
            datosIdProd = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar id del producto: " + e);
        }
    }

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("INSERT INTO proveedor VALUES (?,?,?,?,?,?,?,?,?)");
            objClsConec.Sql.setString(1, nitProv);
            objClsConec.Sql.setInt(2, idProdProv);
            objClsConec.Sql.setString(3, nomProdProv);
            objClsConec.Sql.setString(4, nomProv);
            objClsConec.Sql.setString(5, ciuProv);
            objClsConec.Sql.setString(6, dirProv);
            objClsConec.Sql.setString(7, telProv);
            objClsConec.Sql.setString(8, corProv);
            objClsConec.Sql.setString(9, descProv);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Proveedor registrado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el proveedor: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM proveedor WHERE nitProv=?");
            objClsConec.Sql.setString(1, getNitProv());
            objClsConec.Sql.executeQuery();
            datosProveedor = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al buscar proveedor: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("UPDATE proveedor SET idProdProv=?, nomProdProv=?, nomProv=?, ciuProv=?, dirProv=?, telProv=?, corProv=?, descProv=? WHERE nitProv=?");
            objClsConec.Sql.setInt(1, getIdProdProv());
            objClsConec.Sql.setString(2, getNomProdProv());
            objClsConec.Sql.setString(3, getNomProv());
            objClsConec.Sql.setString(4, getCiuProv());
            objClsConec.Sql.setString(5, getDirProv());
            objClsConec.Sql.setString(6, getTelProv());
            objClsConec.Sql.setString(7, getCorProv());
            objClsConec.Sql.setString(8, getDescProv());
            objClsConec.Sql.setString(9, getNitProv());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Proveedor actualizado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM proveedor WHERE nitProv=?");
            objClsConec.Sql.setString(1, getNitProv());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Proveedor eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor: " + e);
        }
    }

    // Encapsulamiento
    public String getNitProv() {
        return nitProv;
    }

    public void setNitProv(String nitProv) {
        this.nitProv = nitProv;
    }

    public int getIdProdProv() {
        return idProdProv;
    }

    public void setIdProdProv(int idProdProv) {
        this.idProdProv = idProdProv;
    }

    public String getNomProdProv() {
        return nomProdProv;
    }

    public void setNomProdProv(String nomProdProv) {
        this.nomProdProv = nomProdProv;
    }

    public String getNomProv() {
        return nomProv;
    }

    public void setNomProv(String nomProv) {
        this.nomProv = nomProv;
    }

    public String getCiuProv() {
        return ciuProv;
    }

    public void setCiuProv(String ciuProv) {
        this.ciuProv = ciuProv;
    }

    public String getDirProv() {
        return dirProv;
    }

    public void setDirProv(String dirProv) {
        this.dirProv = dirProv;
    }

    public String getTelProv() {
        return telProv;
    }

    public void setTelProv(String telProv) {
        this.telProv = telProv;
    }

    public String getCorProv() {
        return corProv;
    }

    public void setCorProv(String corProv) {
        this.corProv = corProv;
    }

    public String getDescProv() {
        return descProv;
    }

    public void setDescProv(String descProv) {
        this.descProv = descProv;
    }

    public String getNomProdBus() {
        return nomProdBus;
    }

    public void setNomProdBus(String nomProdBus) {
        this.nomProdBus = nomProdBus;
    }

}
