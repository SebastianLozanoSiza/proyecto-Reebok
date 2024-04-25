package com.sebas.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebas.demo.dto.ClienteListDTO;
import com.sebas.demo.dto.ClienteSaveDTO;
import com.sebas.demo.services.ServiceCliente;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {
    
    @Autowired
    private ServiceCliente serviceCliente;

    @GetMapping
    public ResponseEntity<List<ClienteListDTO>> findAll(){
        List<ClienteListDTO> findAll = serviceCliente.findAll();
        if (findAll == null || findAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findAll);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteListDTO> buscarPorEmail(@PathVariable String email){
        ClienteListDTO findByEmail = serviceCliente.findByEmail(email);
        if (findByEmail == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findByEmail);
        }
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<ClienteListDTO> buscarPorCedula(@PathVariable String cedula){
        ClienteListDTO findByCedula = serviceCliente.findByCedula(cedula);
        if (findByCedula == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findByCedula);
        }
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody ClienteSaveDTO cliente, BindingResult result){

        ClienteSaveDTO clienteNew = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {

            clienteNew = serviceCliente.save(cliente);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos de ClienteDTO");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Cliente creado exitosamente");
        response.put("cliente", clienteNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody ClienteSaveDTO clienteDTO, BindingResult result,
            @PathVariable Long id) {

        ClienteSaveDTO clienteUpdate = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {

            clienteUpdate = serviceCliente.update(id, clienteDTO);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Cliente actualizado exitosamente");
        response.put("cliente", clienteUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceCliente.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Cliente eliminado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
