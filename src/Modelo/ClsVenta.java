package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsVenta {

    private int idVent;
    private int idInvVent;
    private int idProdVent;
    private String nitCliVent;
    private String fechVent;
    private String nomCliVent;
    private int canVent;
    private double valVent;
    private String descVent;
    private String nomProdVent;

    private String nomProdBus;
    private String nomCliBus;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosVenta;
    public ResultSet datosProd;
    public ResultSet datosIdProd;
    public ResultSet datosCli;
    public ResultSet datosNitCli;
    public ResultSet datosStockInv;

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

    public void llenarCboCli() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomCli FROM cliente");
            datosCli = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e);
        }
    }

    public void BuscarNitCli() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nitCli FROM cliente WHERE nomCli=?");
            objClsConec.Sql.setString(1, nomCliBus);
            datosNitCli = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar NIT del cliente: " + e);
        }
    }

    public void BuscarStockInventario() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT idInv, cantInv FROM inventario WHERE idProdInv=?");
            objClsConec.Sql.setInt(1, idProdVent);
            datosStockInv = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar stock: " + e);
        }
    }

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "INSERT INTO venta (idInvVent, idProdVent, nitCliVent, fechVent, "
                    + "nomCliVent, canVent, valVent, descVent, nomProdVent) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)");
            objClsConec.Sql.setInt(1, idInvVent);
            objClsConec.Sql.setInt(2, idProdVent);
            objClsConec.Sql.setString(3, nitCliVent);
            objClsConec.Sql.setString(4, fechVent);
            objClsConec.Sql.setString(5, nomCliVent);
            objClsConec.Sql.setInt(6, canVent);
            objClsConec.Sql.setDouble(7, valVent);
            objClsConec.Sql.setString(8, descVent);
            objClsConec.Sql.setString(9, nomProdVent);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e);
        }
    }

    public void DescontarInventario() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("UPDATE inventario SET cantInv = cantInv - ? WHERE idInv=?");
            objClsConec.Sql.setInt(1, canVent);
            objClsConec.Sql.setInt(2, idInvVent);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al descontar inventario: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM venta WHERE idVent=?");
            objClsConec.Sql.setInt(1, idVent);
            datosVenta = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar venta: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM venta WHERE idVent=?");
            objClsConec.Sql.setInt(1, idVent);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Venta eliminada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "UPDATE venta SET idInvVent=?, idProdVent=?, nitCliVent=?, fechVent=?, "
                    + "nomCliVent=?, canVent=?, valVent=?, descVent=?, nomProdVent=? "
                    + "WHERE idVent=?");
            objClsConec.Sql.setInt(1, idInvVent);
            objClsConec.Sql.setInt(2, idProdVent);
            objClsConec.Sql.setString(3, nitCliVent);
            objClsConec.Sql.setString(4, fechVent);
            objClsConec.Sql.setString(5, nomCliVent);
            objClsConec.Sql.setInt(6, canVent);
            objClsConec.Sql.setDouble(7, valVent);
            objClsConec.Sql.setString(8, descVent);
            objClsConec.Sql.setString(9, nomProdVent);
            objClsConec.Sql.setInt(10, idVent);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Venta actualizada exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la venta: " + e);
        }
    }

    public void BuscarPorClienteFecha() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement(
                    "SELECT idVent, idProdVent, nomProdVent, canVent, valVent, "
                    + "fechVent, nomCliVent, nitCliVent, descVent, idInvVent "
                    + "FROM venta WHERE nomCliVent=? AND fechVent=?");
            objClsConec.Sql.setString(1, nomCliVent);
            objClsConec.Sql.setString(2, fechVent);
            datosVenta = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar ventas: " + e);
        }
    }

    public void ObtenerSiguienteId() {
        try {
            objClsConec.Conectar();
            String sql = "SELECT AUTO_INCREMENT FROM information_schema.TABLES "
                    + "WHERE TABLE_SCHEMA = 'solucionesGarantizadas' "
                    + "AND TABLE_NAME = 'venta'";
            objClsConec.Sql = objClsConec.Con.prepareStatement(sql);
            datosVenta = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error al obtener siguiente ID: " + e);
        }
    }

//Encapsulamiento
    public int getIdVent() {
        return idVent;
    }

    public void setIdVent(int idVent) {
        this.idVent = idVent;
    }

    public int getIdInvVent() {
        return idInvVent;
    }

    public void setIdInvVent(int idInvVent) {
        this.idInvVent = idInvVent;
    }

    public int getIdProdVent() {
        return idProdVent;
    }

    public void setIdProdVent(int idProdVent) {
        this.idProdVent = idProdVent;
    }

    public String getNitCliVent() {
        return nitCliVent;
    }

    public void setNitCliVent(String nitCliVent) {
        this.nitCliVent = nitCliVent;
    }

    public String getFechVent() {
        return fechVent;
    }

    public void setFechVent(String fechVent) {
        this.fechVent = fechVent;
    }

    public String getNomCliVent() {
        return nomCliVent;
    }

    public void setNomCliVent(String nomCliVent) {
        this.nomCliVent = nomCliVent;
    }

    public int getCanVent() {
        return canVent;
    }

    public void setCanVent(int canVent) {
        this.canVent = canVent;
    }

    public double getValVent() {
        return valVent;
    }

    public void setValVent(double valVent) {
        this.valVent = valVent;
    }

    public String getDescVent() {
        return descVent;
    }

    public void setDescVent(String descVent) {
        this.descVent = descVent;
    }

    public String getNomProdVent() {
        return nomProdVent;
    }

    public void setNomProdVent(String nomProdVent) {
        this.nomProdVent = nomProdVent;
    }

    public String getNomProdBus() {
        return nomProdBus;
    }

    public void setNomProdBus(String nomProdBus) {
        this.nomProdBus = nomProdBus;
    }

    public String getNomCliBus() {
        return nomCliBus;
    }

    public void setNomCliBus(String nomCliBus) {
        this.nomCliBus = nomCliBus;
    }

}
