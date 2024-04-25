package com.sebas.demo.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaccion_producto")
public class TransaccionProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "producto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Producto producto;

    @JoinColumn(name = "transaccion_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Transaccion transaccion;

    @Min(value = 0, message = "La cantidad elegida debe ser mayor o igual a cero")
    @Column(nullable = false)
    private int cantidad;
    
    @NotNull(message = "El total de los productos seleccionados no puede estar vacío")
    @Min(value = 0, message = "El total de los productos seleccionados debe ser mayor que cero")
    @Column(nullable = false)
    private double totalProductos;

}
