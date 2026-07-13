package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsUsuario {

    private int idUsu;
    private int idRolUsu;
    private String nomRolUsu;
    private String nomUsu;
    private String apeUsu;
    private String telUsu;
    private String corUsu;
    private String conUsu;
    private String nomRolBus;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosUsuario;
    public ResultSet datosRol;       // para llenar el combo
    public ResultSet datosIdRol;     // para obtener idRol según nomRol
    public ResultSet datosConsecutivo;

    public void Consecutivo() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT COUNT(*)+1 FROM usuario");
            objClsConec.Sql.executeQuery();
            datosConsecutivo = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el consecutivo: " + e);
        }
    }

    public void llenarCboRol() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomRol FROM rol");
            objClsConec.Sql.executeQuery();
            datosRol = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar roles: " + e);
        }
    }

    public void BuscarIdRol() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT idRol FROM rol WHERE nomRol=?");
            objClsConec.Sql.setString(1, nomRolBus);
            objClsConec.Sql.executeQuery();
            datosIdRol = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar id del rol: " + e);
        }
    }

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("INSERT INTO usuario (idRolUsu, nomRolUsuRol, nomUsu, apeUsu, telUsu, corUsu, contUsu) VALUES (?,?,?,?,?,?,?)");
            objClsConec.Sql.setInt(1, idRolUsu);
            objClsConec.Sql.setString(2, nomRolUsu);
            objClsConec.Sql.setString(3, nomUsu);
            objClsConec.Sql.setString(4, apeUsu);
            objClsConec.Sql.setString(5, telUsu);
            objClsConec.Sql.setString(6, corUsu);
            objClsConec.Sql.setString(7, conUsu);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM usuario WHERE idUsu=?");
            objClsConec.Sql.setInt(1, getIdUsu());
            objClsConec.Sql.executeQuery();
            datosUsuario = objClsConec.Sql.getResultSet();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al buscar usuario: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("UPDATE usuario SET idRolUsu=?, nomRolUsuRol=?, nomUsu=?, apeUsu=?, telUsu=?, corUsu=?, contUsu=? WHERE idUsu=?");
            objClsConec.Sql.setInt(1, getIdRolUsu());
            objClsConec.Sql.setString(2, getNomRolUsu());
            objClsConec.Sql.setString(3, getNomUsu());
            objClsConec.Sql.setString(4, getApeUsu());
            objClsConec.Sql.setString(5, getTelUsu());
            objClsConec.Sql.setString(6, getCorUsu());
            objClsConec.Sql.setString(7, getConUsu());
            objClsConec.Sql.setInt(8, getIdUsu());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM usuario WHERE idUsu=?");
            objClsConec.Sql.setInt(1, getIdUsu());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + e);
        }
    }

//Encapsulamiento
    public int getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(int idUsu) {
        this.idUsu = idUsu;
    }

    public int getIdRolUsu() {
        return idRolUsu;
    }

    public void setIdRolUsu(int idRolUsu) {
        this.idRolUsu = idRolUsu;
    }

    public String getNomRolUsu() {
        return nomRolUsu;
    }

    public void setNomRolUsu(String nomRolUsu) {
        this.nomRolUsu = nomRolUsu;
    }

    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }

    public String getApeUsu() {
        return apeUsu;
    }

    public void setApeUsu(String apeUsu) {
        this.apeUsu = apeUsu;
    }

    public String getTelUsu() {
        return telUsu;
    }

    public void setTelUsu(String telUsu) {
        this.telUsu = telUsu;
    }

    public String getCorUsu() {
        return corUsu;
    }

    public void setCorUsu(String corUsu) {
        this.corUsu = corUsu;
    }

    public String getConUsu() {
        return conUsu;
    }

    public void setConUsu(String conUsu) {
        this.conUsu = conUsu;
    }

    public String getNomRolBus() {
        return nomRolBus;
    }

    public void setNomRolBus(String nomRolBus) {
        this.nomRolBus = nomRolBus;
    }

}
