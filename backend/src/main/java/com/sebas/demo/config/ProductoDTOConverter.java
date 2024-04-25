package com.sebas.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sebas.demo.dto.ProductoDTO;
import com.sebas.demo.repositories.entities.Producto;

@Component
public class ProductoDTOConverter {
    
    @Autowired
    private ModelMapper dbm;

    @Autowired
    public ProductoDTOConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        this.dbm = modelMapper;
    }

    public ProductoDTO convertToDTO(Producto producto){
        ProductoDTO productoDTO = dbm.map(producto, ProductoDTO.class);
        return productoDTO;
    }

    public Producto convertToEntity(ProductoDTO productoDTO){
        Producto producto = dbm.map(productoDTO, Producto.class);
        return producto;
    }
}
