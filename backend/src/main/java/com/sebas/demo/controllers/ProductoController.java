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

import com.sebas.demo.dto.ProductoDTO;
import com.sebas.demo.repositories.entities.Producto;
import com.sebas.demo.services.ServiceProducto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
public class ProductoController {

    @Autowired
    private ServiceProducto serviceProducto;
    
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll(){
        List<ProductoDTO> findAll = serviceProducto.findAll();
        if (findAll == null || findAll.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findAll);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> findAllById(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Producto producto = serviceProducto.findById(id);
        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody ProductoDTO producto, BindingResult result){

        ProductoDTO productoNew = null;

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

            productoNew = serviceProducto.save(producto);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos de ProductoDTO");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Producto creado exitosamente");
        response.put("producto", productoNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody ProductoDTO productoDTO, BindingResult result,
            @PathVariable Long id) {

        ProductoDTO productoUpdate = null;

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

            productoUpdate = serviceProducto.update(id, productoDTO);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        response.put("mensaje", "Producto actualizado exitosamente");
        response.put("producto", productoUpdate);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            serviceProducto.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar los inserts en la base de datos");
            response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Producto eliminado exitosamente");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
