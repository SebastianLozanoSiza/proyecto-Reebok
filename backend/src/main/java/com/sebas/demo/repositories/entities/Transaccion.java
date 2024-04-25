package com.sebas.demo.repositories.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "transaccion")
public class Transaccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_cliente")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(mappedBy = "transaccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransaccionProducto> transaccionProductos;

    @NotNull(message = "El total de la transaccion no puede estar vacía")
    @Min(value = 0, message = "El total de la transaccion debe ser mayor que cero")
    @Column(nullable = false)
    private double total;
}
