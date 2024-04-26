package com.sebas.demo.config;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebas.demo.dto.ClienteListDTO;
import com.sebas.demo.dto.ClienteSaveDTO;
import com.sebas.demo.repositories.entities.Cliente;

@Component
public class ClienteDTOConverter {
    
    @Autowired
    private ModelMapper dbm;

    @Autowired
    public ClienteDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public ClienteListDTO convertToDTO(Cliente cliente){
        ClienteListDTO clienteDTO = dbm.map(cliente, ClienteListDTO.class);
        return clienteDTO;
    }

    public Cliente convertToEntity(ClienteListDTO clienteDTO){
        Cliente cliente = dbm.map(clienteDTO, Cliente.class);
        return cliente;
    }

    public ClienteSaveDTO convertSaveToDTO(Cliente cliente){
        ClienteSaveDTO clienteDTO = dbm.map(cliente, ClienteSaveDTO.class);
        return clienteDTO;
    }

    public Cliente convertSaveToEntity(ClienteSaveDTO clienteDTO){
        Cliente cliente = dbm.map(clienteDTO, Cliente.class);
        return cliente;
    }
}
