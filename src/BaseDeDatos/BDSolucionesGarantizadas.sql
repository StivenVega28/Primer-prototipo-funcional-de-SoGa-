create database solucionesGarantizadas;
use solucionesGarantizadas;

#Tabla: rol
CREATE TABLE rol (
    idRol    int          	not null auto_increment,
    nomRol   varchar(100) 	not null,
    permRol  varchar(100) 	not null,
    descRol  text 			not null,
    primary key (idRol)
);

#Tabla: producto
#Catálogo de productos vinculados a un proveedor
CREATE TABLE producto (
    idProd      int          	not null auto_increment,
    nomProd     varchar(200) 	not null,
    desProd     text 			not null,
    primary key (idProd)
);

#Tabla: proveedor
#Proveedores de productos
CREATE TABLE proveedor (
    nitProv 	varchar(20)  	not null,
    idProdProv	int         	not null,
    nomProdProv varchar(200) 	not null,
    nomProv 	varchar(200) 	not null,
    ciuProv 	varchar(200)	not null,
    dirProv 	varchar(200)	not null,
    telProv 	varchar(10)		not null,
    corProv 	varchar(100)	not null,
    descProv 	text			not null,
    primary key (nitProv),
    foreign key (idProdProv) references producto(idProd)
);

#Tabla: cliente
#Clientes que realizan compras/ventas
CREATE TABLE cliente (
    nitCli varchar(20)  not null,
    nomCli varchar(200) not null,
    ciuCli varchar(200)	not null,
    sedCli varchar(200)	not null,
    dirCli varchar(200)	not null,
    telCli varchar(10)	not null,
    corCli varchar(100)	not null,
    primary key (nitCli)
);

#Tabla: usuario
#Usuarios del sistema con su rol asignado
CREATE TABLE usuario (
    idUsu    	   	int         	not null auto_increment,
	idRolUsu 	   	int 			not null,
    nomRolUsuRol	varchar(100) 	not null,
    nomUsu   	   	varchar(100) 	not null,
    apeUsu   		varchar(100) 	not null,
    telUsu   		varchar(10)		not null,
    corUsu   		varchar(100)	not null,
    contUsu  		varchar(50)  	not null,
    primary key (idUsu),
    foreign key (idRolUsu) references rol(idRol)
);

#Tabla: compra
#Compras realizadas a proveedores
CREATE TABLE compra (
    idCom      	int         		not null auto_increment,
    nitProvCom 	varchar(20) 		not null,                                  
    idProdCom  	int         		not null,                                    
    canCom     	int         		not null,
    fechCom    	date        		not null,
    valCom     	float       		not null,
    nomProvCom	varchar(200) 		not null,
    nomProdCom     varchar(200) 	not null,
    primary key (idCom),
    foreign key (nitProvCom) references proveedor(nitProv),
    foreign key (idProdCom)  references producto(idProd)
);

#Tabla: inventario
#Stock de productos resultante de las compras

CREATE TABLE inventario (
    idInv     	int not null auto_increment,
    idProdInv 	int not null,                                                
    idCompInv 	int not null,
    nomProdInv 	varchar(200) 	not null,
    cantInv   	int not null,
    primary key (idInv),
    foreign key (idProdInv) references producto(idProd),
    foreign key (idCompInv) references compra(idCom)
);

#Tabla: venta
#Ventas realizadas a clientes
CREATE TABLE venta (
    idVent     		int          	not null auto_increment,
    idInvVent  		int          	not null,
    idProdVent 		int          	not null,                                
    nitCliVent  	varchar(20)  	not null,                                  
    fechVent   		date         	not null,
    nomCliVent 		varchar(200)	not null,
    canVent			int				not null,
    valVent    		float        	not null,
    descVent   		text			not null,
    nomProdVent 	varchar(200) 	not null,
    primary key (idVent),
    foreign key (idInvVent)  references inventario(idInv),
    foreign key (idProdVent) references producto(idProd),
    foreign key (nitCliVent)  references cliente(nitCli)
);

#Tabla: factura
#Facturas generadas por cada venta
CREATE TABLE cotizacion (
    idCot     		int          	not null,
    nitCliCot  		varchar(20) 	not null,
    idInvCot   		int         	not null,
    idProdCot  		int         	not null,
    nomCliCot  		varchar(200)	not null,
    sedCliCot  		varchar(100)	not null,
    dirCliCot  		varchar(200)	not null,
    telCliCot  		varchar(10)		not null,
    corCliCot 		varchar(100)	not null,
    fechCot   		date         	not null,
    medCot     		varchar(100)	not null,
    prodCot    		varchar(500)	not null,
    espCot     		varchar(500)	not null,
    cantCot    		int				not null,
    valUnitCot 		float			not null,
    subTotCot  		float			not null,
    ivaCot    		float			not null,
    totCot     		float			not null,
    valTotUnitCot 	float			not null,
    numInvCot     	varchar(10)		not null,
    itemCot     	int				not null,
    primary key (idCot),
    foreign key (nitCliCot)  references cliente(nitCli),
    foreign key (idInvCot)  references inventario(idInv),
    foreign key (idProdCot) references producto(idProd)
);

INSERT INTO rol (idRol, nomRol, permRol, descRol)
VALUES (1, 'Administrador', 'TOTAL', 'Acceso completo al sistema');

INSERT INTO rol (idRol, nomRol, permRol, descRol)
VALUES (2, 'Mantenimiento', 'PARCIAL', 'Acceso limitado al sistema');

SELECT * FROM rol;

INSERT INTO usuario (idRolUsu, nomRolUsuRol, nomUsu, apeUsu, telUsu, corUsu, contUsu)
VALUES (1, 'Administrador', 'Admin', 'Prueba', '3001234567', 'admin@test.com', '1234');

SELECT * FROM usuario;
SELECT * FROM inventario;

INSERT INTO producto (nomProd, desProd) VALUES
('Pintura Blanca Mate',      'Pintura para interiores color blanco mate, rendimiento 12m² por galón'),
('Pintura Gris Exterior',    'Pintura resistente a la intemperie, ideal para fachadas'),
('Impermeabilizante Techos', 'Sellante impermeabilizante para techos planos y losas'),
('Estuco Plástico',          'Estuco listo para usar, acabado liso para paredes interiores'),
('Sellador de Fisuras',      'Producto para reparar grietas y fisuras en muros y paredes');

SELECT * FROM producto;

INSERT INTO proveedor (nitProv, idProdProv, nomProdProv, nomProv, ciuProv, dirProv, telProv, corProv, descProv) VALUES
('900111222-1', 1, 'Pintura Blanca Mate',      'Pinturas Corona S.A.',      'Bogotá',     'Cra 30 # 45-20',   '6011234567', 'ventas@corona.com',     'Proveedor principal de pinturas de interiores'),
('900333444-2', 2, 'Pintura Gris Exterior',    'Sherwin Williams Colombia', 'Medellín',   'Calle 50 # 12-35', '6042345678', 'colombia@sherwin.com',  'Distribuidor de pinturas de alto desempeño'),
('900555666-3', 3, 'Impermeabilizante Techos', 'Sika Colombia Ltda.',       'Cali',       'Av. 3N # 24-60',   '6023456789', 'sika@sikacol.com',      'Especialista en impermeabilización y sellado'),
('900777888-4', 4, 'Estuco Plástico',          'Procoat S.A.S.',            'Bucaramanga', 'Cra 27 # 48-15',  '6074567890', 'info@procoat.com',      'Fabricante de estucos y acabados para construcción'),
('900999000-5', 5, 'Sellador de Fisuras',      'Súper de Alimentos S.A.',   'Barranquilla','Calle 72 # 43-10','6055678901', 'ventas@supermat.com',   'Distribuidor de materiales de construcción y reparación');

SELECT * FROM proveedor;

INSERT INTO cliente (nitCli, nomCli, ciuCli, sedCli, dirCli, telCli, corCli) VALUES
('800100200-1', 'Constructora Andina S.A.S.',    'Bogotá',      'Sede Norte',   'Cra 15 # 85-40',   '3101234567', 'contacto@andina.com'),
('800300400-2', 'Inmobiliaria Los Andes Ltda.',  'Medellín',    'Sede Centro',  'Calle 33 # 65-20', '3122345678', 'info@losandes.com'),
('800500600-3', 'Obras Civiles del Valle S.A.',  'Cali',        'Sede Sur',     'Av. 6 # 22-18',    '3153456789', 'ventas@valleobras.com'),
('800700800-4', 'Remodelaciones Santander S.A.', 'Bucaramanga', 'Sede Única',   'Cra 33 # 51-10',   '3174567890', 'rem@santander.com'),
('800900100-5', 'Proyectos Caribe Ltda.',        'Barranquilla','Sede Principal','Calle 84 # 45-22', '3195678901', 'proyectos@caribe.com');

SELECT * FROM cliente;
SELECT * FROM venta;

SHOW COLUMNS FROM cotizacion LIKE 'idInvCot';
ALTER TABLE cotizacion MODIFY COLUMN idInvCot INT NULL;
