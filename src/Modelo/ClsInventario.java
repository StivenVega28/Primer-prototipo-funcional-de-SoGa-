package Modelo;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
import Controlador.ClsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsInventario {

    private String nomProdFiltro;

    ClsConexion objClsConec = new ClsConexion();

    public ResultSet datosInventario;
    public ResultSet datosProd;

    public void llenarCboProd() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomProd FROM producto");
            datosProd = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e);
        }
    }

    public void ConsultarTodos() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomProdInv, idProdInv, cantInv FROM inventario");
            datosInventario = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar inventario: " + e);
        }
    }

    public void ConsultarPorProducto() {
        try {
            objClsConec.Conectar();
            objClsConec.Sql = objClsConec.Con.prepareStatement("SELECT nomProdInv, idProdInv, cantInv FROM inventario WHERE nomProdInv=?");
            objClsConec.Sql.setString(1, nomProdFiltro);
            datosInventario = objClsConec.Sql.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar inventario por producto: " + e);
        }
    }


//Encapsulamiento
    public String getNomProdFiltro() {
        return nomProdFiltro;
    }

    public void setNomProdFiltro(String nomProdFiltro) {
        this.nomProdFiltro = nomProdFiltro;
    }

}
