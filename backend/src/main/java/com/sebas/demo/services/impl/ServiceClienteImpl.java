package com.sebas.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sebas.demo.config.ClienteDTOConverter;
import com.sebas.demo.dto.ClienteListDTO;
import com.sebas.demo.dto.ClienteSaveDTO;
import com.sebas.demo.repositories.RepositoryCliente;
import com.sebas.demo.repositories.entities.Cliente;
import com.sebas.demo.services.ServiceCliente;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceClienteImpl implements ServiceCliente{
    
    @Autowired
    private RepositoryCliente repositoryCliente;

    @Autowired
    private ClienteDTOConverter convert;
    
    @Override
    @Transactional(readOnly = true)
    public List<ClienteListDTO> findAll() {
        List<Cliente> clientes = (List<Cliente>) repositoryCliente.findAll();
        return clientes.stream()
                    .map(cliente -> convert.convertToDTO(cliente))
                    .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteListDTO findByEmail(String email) {
        Cliente cliente = repositoryCliente.findByEmail(email);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        return convert.convertToDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteListDTO findByCedula(String cedula) {
        Cliente cliente = repositoryCliente.findByCedula(cedula);
        if (cliente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        return convert.convertToDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteSaveDTO save(ClienteSaveDTO clienteDTO) {
        Cliente cliente = convert.convertSaveToEntity(clienteDTO);
        cliente = repositoryCliente.save(cliente);
        return convert.convertSaveToDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteSaveDTO update(Long id, ClienteSaveDTO clienteDTO) {
        Optional<Cliente> clienteCurrentOptional = repositoryCliente.findById(id);
        if (clienteCurrentOptional.isPresent()) {
            Cliente clienteCurrent = clienteCurrentOptional.get();
            clienteCurrent.setCedula(clienteDTO.getCedula());
            clienteCurrent.setNombre(clienteDTO.getNombre());
            clienteCurrent.setApellido(clienteDTO.getApellido());
            clienteCurrent.setEmail(clienteDTO.getEmail());
            clienteCurrent.setPassword(clienteDTO.getPassword());
            clienteCurrent.setTelefono(clienteDTO.getTelefono());
            clienteCurrent.setDireccion(clienteDTO.getDireccion());
            repositoryCliente.save(clienteCurrent);
            return convert.convertSaveToDTO(clienteCurrent);
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Cliente> clienteOptional = repositoryCliente.findById(id);
        if (clienteOptional.isPresent()) {
            repositoryCliente.delete(clienteOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
    }
    
}
