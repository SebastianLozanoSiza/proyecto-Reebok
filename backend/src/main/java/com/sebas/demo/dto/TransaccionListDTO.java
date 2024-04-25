package com.sebas.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionListDTO {
    
    private Long id;
    private String cedulaCliente;
    private String nombreCliente;
    private String emailCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private List<TransaccionProductoListDTO> listaProductos;
    private double totalTransaccion;
}
