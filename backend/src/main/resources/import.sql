INSERT INTO cliente (cedula, nombre, apellido, email, pwd, telefono, direccion)
VALUES ('1234567890', 'Juan', 'Perez', 'juan.perez@example.com', 'password123', '555-1234', 'Calle 123');
INSERT INTO cliente (cedula, nombre, apellido, email, pwd, telefono, direccion)
VALUES ('0987654321', 'Maria', 'Lopez', 'maria.lopez@example.com', 'password456', '555-5678', 'Avenida 456');
INSERT INTO cliente (cedula, nombre, apellido, email, pwd, telefono, direccion)
VALUES ('1357924680', 'Carlos', 'Ramirez', 'carlos.ramirez@example.com', 'password789', '555-2468', 'Carrera 789');
INSERT INTO cliente (cedula, nombre, apellido, email, pwd, telefono, direccion)
VALUES ('2468013579', 'Ana', 'Martinez', 'ana.martinez@example.com', 'passwordabc', '555-1357', 'Calle 987');
INSERT INTO cliente (cedula, nombre, apellido, email, pwd, telefono, direccion)
VALUES ('3698521470', 'Luisa', 'Gomez', 'luisa.gomez@example.com', 'passworddef', '555-9876', 'Avenida 159');
INSERT INTO cliente (cedula, nombre, apellido, email, pwd, telefono, direccion)
VALUES ('7410258963', 'Pedro', 'Fernandez', 'pedro.fernandez@example.com', 'passwordghi', '555-3698', 'Carrera 741');

INSERT INTO producto (nombre, descripcion, precio, stock)
VALUES ('Camiseta Reebok', 'Camiseta deportiva de alta calidad', 39.99, 100);
INSERT INTO producto (nombre, descripcion, precio, stock)
VALUES ('Zapatillas Running', 'Zapatillas para correr de gran confort', 79.99, 50);
INSERT INTO producto (nombre, descripcion, precio, stock)
VALUES ('Mochila Deportiva', 'Mochila espaciosa para llevar tus implementos', 49.99, 75);
INSERT INTO producto (nombre, descripcion, precio, stock)
VALUES ('Pantalon Deportivo', 'Pantalon cómodo para hacer deporte', 29.99, 120);

INSERT INTO transaccion (id_cliente, total)
VALUES (1, 119.98);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (1, 1, 1, 39.99);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (1, 2, 1, 79.99);

INSERT INTO transaccion (id_cliente, total)
VALUES (2, 129.98);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (2, 3, 1, 49.99);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (2, 4, 1, 29.99);

INSERT INTO transaccion (id_cliente, total)
VALUES (3, 169.97);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (3, 1, 1, 39.99);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (3, 2, 1, 79.99);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (3, 3, 1, 49.99);

INSERT INTO transaccion (id_cliente, total)
VALUES (4, 29.99);

INSERT INTO transaccion_producto (transaccion_id, producto_id, cantidad, total_productos)
VALUES (4, 4, 1, 29.99);

INSERT INTO roles (role_name, id_cliente) VALUES ('ROLE_USER', 1);      
INSERT INTO roles (role_name, id_cliente) VALUES ('ROLE_USER', 2); 
INSERT INTO roles (role_name, id_cliente) VALUES ('ROLE_USER', 3); 
INSERT INTO roles (role_name, id_cliente) VALUES ('ROLE_USER', 4); 
INSERT INTO roles (role_name, id_cliente) VALUES ('ROLE_ADMIN', 5); 
INSERT INTO roles (role_name, id_cliente) VALUES ('ROLE_ADMIN', 6); 