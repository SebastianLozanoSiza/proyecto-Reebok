package com.sebas.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionProductoListDTO {
    
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double totalProducto;
}
