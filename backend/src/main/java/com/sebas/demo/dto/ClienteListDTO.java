package com.sebas.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteListDTO {
    
    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String email;
    @JsonIgnore
    private String password;
    private String telefono;
    private String direccion;
}
