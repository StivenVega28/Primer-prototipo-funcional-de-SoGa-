package Controlador;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class ClsConexion {

    public PreparedStatement Sql;
    public Connection Con;

    public void Conectar() {
        String Db = "jdbc:mysql://localhost:3306/solucionesGarantizadas";
        String Usuario = "root";
        String Password = "1234";

        try {
            String Controlador = "com.mysql.cj.jdbc.Driver";
            Class.forName(Controlador);
            Con = DriverManager.getConnection(Db, Usuario, Password);
            //JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectar: " + e);
        }
    }

    public void Cerrar() {

    }
}
