package com.sebas.demo.services;

import java.util.List;

import com.sebas.demo.dto.ProductoDTO;
import com.sebas.demo.repositories.entities.Producto;

public interface ServiceProducto {
    
    List<ProductoDTO> findAll();

    Producto findById(Long id);
    
    ProductoDTO save(ProductoDTO productoDTO);

    ProductoDTO update(Long id, ProductoDTO productoDTO);

    void delete(Long id);
}
