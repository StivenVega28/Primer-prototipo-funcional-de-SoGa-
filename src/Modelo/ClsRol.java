package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsRol {

    private int idRol;
    private String nomRol;
    private String permRol;
    private String descRol;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosRol;
    public ResultSet datosConsecutivo;

    public void Consecutivo() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT COUNT(*)+1 FROM rol");
            datosConsecutivo = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el consecutivo: " + e);
        }
    }

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("INSERT INTO rol (nomRol, permRol, descRol) VALUES (?,?,?)");
            objClsConec.Sql.setString(1, nomRol);
            objClsConec.Sql.setString(2, permRol);
            objClsConec.Sql.setString(3, descRol);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Rol registrado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el rol: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM rol WHERE idRol=?");
            objClsConec.Sql.setInt(1, getIdRol());
            objClsConec.Sql.executeQuery();
            datosRol = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al buscar rol: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("UPDATE rol SET nomRol=?, permRol=?, descRol=? WHERE idRol=?");
            objClsConec.Sql.setString(1, getNomRol());
            objClsConec.Sql.setString(2, getPermRol());
            objClsConec.Sql.setString(3, getDescRol());
            objClsConec.Sql.setInt(4, getIdRol());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Rol actualizado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el rol: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM rol WHERE idRol=?");
            objClsConec.Sql.setInt(1, getIdRol());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Rol eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el rol: " + e);
        }
    }

//Encapsulamiento
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNomRol() {
        return nomRol;
    }

    public void setNomRol(String nomRol) {
        this.nomRol = nomRol;
    }

    public String getPermRol() {
        return permRol;
    }

    public void setPermRol(String permRol) {
        this.permRol = permRol;
    }

    public String getDescRol() {
        return descRol;
    }

    public void setDescRol(String descRol) {
        this.descRol = descRol;
    }

}
