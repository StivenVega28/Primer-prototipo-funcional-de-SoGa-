# Soluciones Garantizadas

Sistema de gestión empresarial de escritorio desarrollado en Java Swing para administrar las operaciones de una PYME, incluyendo gestión de clientes, productos, proveedores, inventario, ventas, compras y cotizaciones.

## 🌟 Características

- **Gestión de Clientes**: Registro, consulta, actualización y eliminación de clientes
- **Gestión de Productos**: Catálogo de productos con control de inventario
- **Gestión de Proveedores**: Administración de proveedores
- **Control de Inventario**: Gestión de stock y existencias
- **Proceso de Ventas**: Registro de ventas con descuento automático de inventario
- **Gestión de Compras**: Registro de compras a proveedores
- **Cotizaciones**: Generación de cotizaciones para clientes
- **Control de Usuarios**: Gestión de usuarios y roles del sistema
- **Sistema de Permisos**: Dos roles de usuario:
  - **Administrador**: Acceso completo a todos los módulos
  - **Mantenimiento**: Acceso solo al módulo de Inventario

## 📋 Requisitos del Sistema

- **Java Development Kit (JDK)**: Version 1.8 o superior
- **MySQL Server**: Version 5.7 o superior
- **NetBeans IDE**: Recomendado para desarrollo (opcional para ejecución)
- **Sistema Operativo**: Windows, Linux o macOS

## 🔧 Instalación y Configuración

### 1. Configuración de la Base de Datos

```sql
-- Crear la base de datos
CREATE DATABASE solucionesGarantizadas;

-- Crear las tablas necesarias (ejemplo)
CREATE TABLE cliente (
    nitCli VARCHAR(20) PRIMARY KEY,
    nomCli VARCHAR(100),
    ciuCli VARCHAR(50),
    sedCli VARCHAR(50),
    dirCli VARCHAR(100),
    telCli VARCHAR(20),
    corCli VARCHAR(100)
);

CREATE TABLE producto (
    idProd INT AUTO_INCREMENT PRIMARY KEY,
    nomProd VARCHAR(100),
    desProd TEXT
);

-- (Añadir resto de tablas según el esquema)
```

### 2. Configuración de Conexión

Editar el archivo `src/Controlador/ClsConexion.java` si es necesario modificar las credenciales:

```java
String Db = "jdbc:mysql://localhost:3306/solucionesGarantizadas";
String Usuario = "root";
String Password = "1234";
```

### 3. Compilación y Ejecución

#### Opción A: Desde NetBeans
1. Abrir el proyecto en NetBeans
2. Ejecutar `Clean and Build`
3. Ejecutar `Run`

#### Opción B: Desde línea de comandos (Ant)
```bash
# Compilar el proyecto
ant clean build

# Ejecutar el proyecto
ant run
```

#### Opción C: Ejecutar el JAR
```bash
java -jar dist/solucionesGarantizadas.jar
```

## 🚀 Uso

### Inicio de Sesión

1. Ejecutar la aplicación
2. Ingresar usuario y contraseña
3. El sistema asignará permisos según el rol del usuario

### Navegación

El menú principal (`FrmIndex`) muestra los módulos disponibles según los permisos del usuario:
- **Administrador**: Todos los módulos accesibles
- **Mantenimiento**: Solo módulo de Inventario

## 📁 Estructura del Proyecto

```
solucionesGarantizadas/
├── src/
│   ├── Controlador/
│   │   ├── ClsConexion.java      # Gestión de conexión MySQL
│   │   └── ClsSesion.java        # Control de sesiones y permisos
│   ├── Modelo/
│   │   ├── ClsCliente.java       # Lógica de negocio clientes
│   │   ├── ClsCompra.java        # Lógica de negocio compras
│   │   ├── ClsCotizacion.java    # Lógica de negocio cotizaciones
│   │   ├── ClsInventario.java    # Lógica de negocio inventario
│   │   ├── ClsLogin.java         # Validación de usuarios
│   │   ├── ClsProducto.java      # Lógica de negocio productos
│   │   ├── ClsProveedor.java     # Lógica de negocio proveedores
│   │   ├── ClsRol.java           # Gestión de roles
│   │   ├── ClsUsuario.java       # Gestión de usuarios
│   │   └── ClsVenta.java         # Lógica de negocio ventas
│   ├── Vista/
│   │   ├── FrmCliente.java       # Formulario clientes
│   │   ├── FrmCompra.java        # Formulario compras
│   │   ├── FrmCotizacion.java    # Formulario cotizaciones
│   │   ├── FrmIndex.java         # Menú principal
│   │   ├── FrmInventario.java    # Formulario inventario
│   │   ├── FrmLogin.java         # Formulario login
│   │   ├── FrmProducto.java      # Formulario productos
│   │   ├── FrmProveedor.java     # Formulario proveedores
│   │   ├── FrmRol.java           # Formulario roles
│   │   ├── FrmUsuario.java       # Formulario usuarios
│   │   └── FrmVenta.java         # Formulario ventas
│   └── Librerias/
│       ├── mysql-connector-j-9.6.0.jar
│       └── jcalendar-1.4.jar
├── nbproject/                    # Configuración NetBeans
├── build.xml                     # Script de compilación Ant
├── manifest.mf                   # Manifiesto JAR
└── README.md                     # Este archivo
```

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 1.8
- **Interfaz Gráfica**: Java Swing
- **Base de Datos**: MySQL
- **Build System**: Apache Ant
- **IDE**: NetBeans
- **Driver JDBC**: MySQL Connector J 9.6.0
- **Componentes Adicionales**: JCalendar 1.4

## 👥 Autores

- **Stiven Aparicio Vega**
- **Carlos Andres Castañeda Monsalve**

## 📄 Licencia

Este proyecto es desarrollado con fines educativos y de gestión empresarial.

## 🔜 Mejoras Futuras

- Implementar validación de formularios más robusta
- Agregar reportes en PDF
- Implementar backup de base de datos
