package com.sebas.demo.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "La cedula del cliente no puede estar vacia")
    @Column(nullable = false, unique = true)
    private String cedula;

    @NotEmpty(message = "El nombre del cliente no puede estar vacio")
    @Column(nullable = false)
    private String nombre;

    @NotEmpty(message = "El apellido del cliente no puede estar vacio")
    @Column(nullable = false)
    private String apellido;

    @NotEmpty(message = "El email del cliente no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "pwd")
    @JsonIgnore
    private String password;

    @NotEmpty(message = "El telefono del cliente no puede estar vacio")
    @Column(nullable = false, unique = true)
    private String telefono;

    @NotEmpty(message = "La direccion del cliente no puede estar vacio")
    @Column(nullable = false)
    private String direccion;
}
