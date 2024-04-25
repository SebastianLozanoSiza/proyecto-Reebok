package com.sebas.demo.services;

import java.util.List;

import com.sebas.demo.dto.TransaccionListDTO;
import com.sebas.demo.dto.TransaccionSaveDTO;
import com.sebas.demo.repositories.entities.Transaccion;

public interface ServiceTransaccion {
    
    List<TransaccionListDTO> findAll();

    Transaccion findById(Long id);
    
    TransaccionSaveDTO save(TransaccionSaveDTO transaccionSaveDTO);

    TransaccionSaveDTO update(Long id, TransaccionSaveDTO transaccionSaveDTO);

    void delete(Long id);
}
