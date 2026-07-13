package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsCliente {

    private String nitCli;
    private String nomCli;
    private String ciuCli;
    private String sedCli;
    private String dirCli;
    private String telCli;
    private String corCli;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosCliente;

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("INSERT INTO cliente VALUES (?,?,?,?,?,?,?)");
            objClsConec.Sql.setString(1, nitCli);
            objClsConec.Sql.setString(2, nomCli);
            objClsConec.Sql.setString(3, ciuCli);
            objClsConec.Sql.setString(4, sedCli);
            objClsConec.Sql.setString(5, dirCli);
            objClsConec.Sql.setString(6, telCli);
            objClsConec.Sql.setString(7, corCli);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el cliente: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM cliente WHERE nitCli=?");
            objClsConec.Sql.setString(1, getNitCli());
            objClsConec.Sql.executeQuery();
            datosCliente = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al buscar cliente: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("UPDATE cliente SET nomCli=?, ciuCli=?, sedCli=?, dirCli=?, telCli=?, corCli=? WHERE nitCli=?");
            objClsConec.Sql.setString(1, getNomCli());
            objClsConec.Sql.setString(2, getCiuCli());
            objClsConec.Sql.setString(3, getSedCli());
            objClsConec.Sql.setString(4, getDirCli());
            objClsConec.Sql.setString(5, getTelCli());
            objClsConec.Sql.setString(6, getCorCli());
            objClsConec.Sql.setString(7, getNitCli());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM cliente WHERE nitCli=?");
            objClsConec.Sql.setString(1, getNitCli());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e);
        }
    }

//Encapsulamiento
    public String getNitCli() {
        return nitCli;
    }

    public void setNitCli(String nitCli) {
        this.nitCli = nitCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public String getCiuCli() {
        return ciuCli;
    }

    public void setCiuCli(String ciuCli) {
        this.ciuCli = ciuCli;
    }

    public String getSedCli() {
        return sedCli;
    }

    public void setSedCli(String sedCli) {
        this.sedCli = sedCli;
    }

    public String getDirCli() {
        return dirCli;
    }

    public void setDirCli(String dirCli) {
        this.dirCli = dirCli;
    }

    public String getTelCli() {
        return telCli;
    }

    public void setTelCli(String telCli) {
        this.telCli = telCli;
    }

    public String getCorCli() {
        return corCli;
    }

    public void setCorCli(String corCli) {
        this.corCli = corCli;
    }

}
