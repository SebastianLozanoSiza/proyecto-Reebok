package com.sebas.demo.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sebas.demo.config.ProductoDTOConverter;
import com.sebas.demo.dto.ProductoDTO;
import com.sebas.demo.repositories.RepositoryProducto;
import com.sebas.demo.repositories.entities.Producto;
import com.sebas.demo.services.ServiceProducto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServiceProductoImpl implements ServiceProducto{
    
    @Autowired
    private RepositoryProducto repositoryProducto;

    @Autowired
    private ProductoDTOConverter convert;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAll() {
        List<Producto> productos = (List<Producto>) repositoryProducto.findAll();
        return productos.stream()
                    .map(producto -> convert.convertToDTO(producto))
                    .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return repositoryProducto.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error! Producto no existente"));
    }

    @Override
    @Transactional
    public ProductoDTO save(ProductoDTO productoDTO) {
        Producto producto = convert.convertToEntity(productoDTO);
        producto = repositoryProducto.save(producto);
        return convert.convertToDTO(producto);
    }

    @Override
    @Transactional
    public ProductoDTO update(Long id, ProductoDTO productoDTO) {
        Optional<Producto> productoCurrentOptional = repositoryProducto.findById(id);
        if (productoCurrentOptional.isPresent()) {
            Producto productoCurrent = productoCurrentOptional.get();
            productoCurrent.setNombre(productoDTO.getNombre());
            productoCurrent.setDescripcion(productoDTO.getDescripcion());
            productoCurrent.setPrecio(productoDTO.getPrecio());
            productoCurrent.setStock(productoDTO.getStock());
            return convert.convertToDTO(productoCurrent);
        }
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Producto> productoOptional = repositoryProducto.findById(id);
        if (productoOptional.isPresent()) {
            repositoryProducto.delete(productoOptional.get());
        }else{
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Error! Producto no existente");
        }
    }
    
}
