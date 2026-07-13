package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsCompra {

    private int idCom;
    private String nitProvCom;
    private int idProdCom;
    private int canCom;
    private String fechCom;
    private double valCom;
    private String nomProvCom;
    private String nomProdCom;

    private String nomProvBus;
    private String nomProdBus;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosCompra;
    public ResultSet datosConsecutivo;
    public ResultSet datosProv;
    public ResultSet datosNitProv;
    public ResultSet datosProd;
    public ResultSet datosIdProd;

    public void Consecutivo() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT COUNT(*)+1 FROM compra");
            datosConsecutivo = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el consecutivo: " + e);
        }
    }

    public void llenarCboProv() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomProv FROM proveedor");
            datosProv = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores: " + e);
        }
    }

    public void BuscarDatosProv() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nitProv FROM proveedor WHERE nomProv=?");
            objClsConec.Sql.setString(1, nomProvBus);
            datosNitProv = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar datos del proveedor: " + e);
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
            JOptionPane.showMessageDialog(null, "Error al buscar id del producto: " + e);
        }
    }

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "INSERT INTO compra (nitProvCom, idProdCom, canCom, fechCom, valCom, nomProvCom, nomProdCom) "
                    + "VALUES (?,?,?,?,?,?,?)",
                    java.sql.Statement.RETURN_GENERATED_KEYS);
            objClsConec.Sql.setString(1, nitProvCom);
            objClsConec.Sql.setInt(2, idProdCom);
            objClsConec.Sql.setInt(3, canCom);
            objClsConec.Sql.setString(4, fechCom);
            objClsConec.Sql.setDouble(5, valCom);
            objClsConec.Sql.setString(6, nomProvCom);
            objClsConec.Sql.setString(7, nomProdCom);
            objClsConec.Sql.executeUpdate();
            ResultSet llaves = objClsConec.Sql.getGeneratedKeys();
            if (llaves.next()) {
                idCom = llaves.getInt(1);
            }
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Compra registrada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la compra: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM compra WHERE idCom=?");
            objClsConec.Sql.setInt(1, getIdCom());
            datosCompra = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al buscar compra: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "UPDATE compra SET nitProvCom=?, idProdCom=?, canCom=?, fechCom=?, valCom=?, nomProvCom=?, nomProdCom=? "
                    + "WHERE idCom=?");
            objClsConec.Sql.setString(1, getNitProvCom());
            objClsConec.Sql.setInt(2, getIdProdCom());
            objClsConec.Sql.setInt(3, getCanCom());
            objClsConec.Sql.setString(4, getFechCom());
            objClsConec.Sql.setDouble(5, getValCom());
            objClsConec.Sql.setString(6, getNomProvCom());
            objClsConec.Sql.setString(7, getNomProdCom());
            objClsConec.Sql.setInt(8, getIdCom());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Compra actualizada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la compra: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM compra WHERE idCom=?");
            objClsConec.Sql.setInt(1, getIdCom());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Compra eliminada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la compra: " + e);
        }
    }

    public void RegistrarEnInventario() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "INSERT INTO inventario (idProdInv, idCompInv, nomProdInv, cantInv) "
                    + "VALUES (?,?,?,?)");
            objClsConec.Sql.setInt(1, idProdCom);
            objClsConec.Sql.setInt(2, idCom);
            objClsConec.Sql.setString(3, nomProdCom);
            objClsConec.Sql.setInt(4, canCom);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar en inventario: " + e);
        }
    }

//Encapsulamiento
    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public String getNitProvCom() {
        return nitProvCom;
    }

    public void setNitProvCom(String nitProvCom) {
        this.nitProvCom = nitProvCom;
    }

    public int getIdProdCom() {
        return idProdCom;
    }

    public void setIdProdCom(int idProdCom) {
        this.idProdCom = idProdCom;
    }

    public int getCanCom() {
        return canCom;
    }

    public void setCanCom(int canCom) {
        this.canCom = canCom;
    }

    public String getFechCom() {
        return fechCom;
    }

    public void setFechCom(String fechCom) {
        this.fechCom = fechCom;
    }

    public double getValCom() {
        return valCom;
    }

    public void setValCom(double valCom) {
        this.valCom = valCom;
    }

    public String getNomProvCom() {
        return nomProvCom;
    }

    public void setNomProvCom(String nomProvCom) {
        this.nomProvCom = nomProvCom;
    }

    public String getNomProdCom() {
        return nomProdCom;
    }

    public void setNomProdCom(String nomProdCom) {
        this.nomProdCom = nomProdCom;
    }

    public String getNomProvBus() {
        return nomProvBus;
    }

    public void setNomProvBus(String nomProvBus) {
        this.nomProvBus = nomProvBus;
    }

    public String getNomProdBus() {
        return nomProdBus;
    }

    public void setNomProdBus(String nomProdBus) {
        this.nomProdBus = nomProdBus;
    }

}
