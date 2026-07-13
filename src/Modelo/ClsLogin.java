package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsLogin {

    private String usuLogin;
    private String conLogin;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosLogin;

    public void Validar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomRolUsuRol FROM usuario WHERE nomUsu=? AND contUsu=?");
            objClsConec.Sql.setString(1, usuLogin);
            objClsConec.Sql.setString(2, conLogin);
            datosLogin = objClsConec.Sql.executeQuery(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar el usuario: " + e);
        }
    }

//Encapsulamiento
    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getConLogin() {
        return conLogin;
    }

    public void setConLogin(String conLogin) {
        this.conLogin = conLogin;
    }
}
