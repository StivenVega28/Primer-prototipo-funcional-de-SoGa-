package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsProducto {

    private int idProd;
    private String nomProd;
    private String desProd;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosProducto;
    public ResultSet datosConsecutivo;

    public void Consecutivo() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT COUNT(*)+1 FROM producto");
            datosConsecutivo = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el consecutivo: " + e);
        }
    }

    public void Registrar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("INSERT INTO producto (nomProd, desProd) VALUES (?,?)");
            objClsConec.Sql.setString(1, nomProd);
            objClsConec.Sql.setString(2, desProd);
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Producto registrado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el producto: " + e);
        }
    }

    public void Buscar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT * FROM producto WHERE idProd=?");
            objClsConec.Sql.setInt(1, getIdProd());
            datosProducto = objClsConec.Sql.executeQuery(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al buscar producto: " + e);
        }
    }

    public void Actualizar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("UPDATE producto SET nomProd=?, desProd=? WHERE idProd=?");
            objClsConec.Sql.setString(1, getNomProd());
            objClsConec.Sql.setString(2, getDesProd());
            objClsConec.Sql.setInt(3, getIdProd());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + e);
        }
    }

    public void Eliminar() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("DELETE FROM producto WHERE idProd=?");
            objClsConec.Sql.setInt(1, getIdProd());
            objClsConec.Sql.executeUpdate();
            objClsConec.Cerrar();
            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e);
        }
    }

//Encapsulamiento
    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public String getDesProd() {
        return desProd;
    }

    public void setDesProd(String desProd) {
        this.desProd = desProd;
    }

}
