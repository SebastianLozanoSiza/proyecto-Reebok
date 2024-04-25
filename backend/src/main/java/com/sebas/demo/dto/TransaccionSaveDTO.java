package com.sebas.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransaccionSaveDTO {
    
    private String cedulaCliente;
    private List<TransaccionProductoSaveDTO> productos;
    private double total;
}
