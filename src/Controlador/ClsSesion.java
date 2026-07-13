package Controlador;

//Autores Stiven Aparicio Vega y Carlos Andres Castañeda Monsalve
public class ClsSesion {

    public enum Permiso {
        ADMINISTRADOR,
        MANTENIMIENTO

    }

    public static String usuarioActivo = "";
    public static Permiso permiso;

    // Puede usar todos los formularios
    public static boolean esAdministrador() {
        return permiso == Permiso.ADMINISTRADOR;
    }

    // Solo puede usar FrmInventario
    public static boolean esMantenimiento() {
        return permiso == Permiso.MANTENIMIENTO;
    }
}
