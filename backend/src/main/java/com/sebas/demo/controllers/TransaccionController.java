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

import com.sebas.demo.dto.TransaccionListDTO;
import com.sebas.demo.dto.TransaccionSaveDTO;
import com.sebas.demo.repositories.entities.Transaccion;
import com.sebas.demo.services.ServiceTransaccion;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transacciones")
@AllArgsConstructor
public class TransaccionController {

    @Autowired
    private ServiceTransaccion serviceTransaccion;

    @GetMapping
    public ResponseEntity<List<TransaccionListDTO>> findAll() {
        List<TransaccionListDTO> findAll = serviceTransaccion.findAll();
        if (findAll == null || findAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(findAll);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findAllById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Transaccion transaccion = serviceTransaccion.findById(id);
        response.put("transaccion", transaccion);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody TransaccionSaveDTO transaccionSaveDTO,
            BindingResult result) {
        TransaccionSaveDTO transaccionNew = null;
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
            transaccionNew = serviceTransaccion.save(transaccionSaveDTO);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos de TransaccionDTO");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Transaccion creado exitosamente");
        response.put("transaccion", transaccionNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody TransaccionSaveDTO transaccionSaveDTO,
            BindingResult result,
            @PathVariable Long id) {

        TransaccionSaveDTO transaccionUpdate = null;

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

            transaccionUpdate = serviceTransaccion.update(id, transaccionSaveDTO);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Transaccion actualizado exitosamente");
        response.put("transaccion", transaccionUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceTransaccion.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Transaccion eliminada exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
