package com.sebas.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
}
