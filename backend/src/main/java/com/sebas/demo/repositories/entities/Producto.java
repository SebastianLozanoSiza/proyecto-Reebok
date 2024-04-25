package com.sebas.demo.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "producto")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del producto no puede estar vacio")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "La descripcion del producto no puede estar vacia")
    @Column(nullable = false)
    private String descripcion;

    @NotNull(message = "El precio del producto no puede estar vacío")
    @Min(value = 0, message = "El precio del producto debe ser mayor que cero")
    @Column(nullable = false)
    private double precio;

    @Min(value = 0, message = "La cantidad disponible debe ser mayor o igual a cero")
    @Column(nullable = false)
    private int stock;
}
