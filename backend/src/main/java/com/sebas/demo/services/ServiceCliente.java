package com.sebas.demo.services;

import java.util.List;

import com.sebas.demo.dto.ClienteListDTO;
import com.sebas.demo.dto.ClienteSaveDTO;

public interface ServiceCliente {
    
    List<ClienteListDTO> findAll();

    ClienteListDTO findByEmail(String email);

    ClienteListDTO findByCedula(String cedula);
    
    ClienteSaveDTO save(ClienteSaveDTO clienteDTO);

    ClienteSaveDTO update(Long id, ClienteSaveDTO clienteDTO);

    void delete(Long id);
}
