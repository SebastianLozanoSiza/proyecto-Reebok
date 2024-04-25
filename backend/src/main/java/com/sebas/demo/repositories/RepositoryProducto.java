package com.sebas.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sebas.demo.repositories.entities.Producto;

public interface RepositoryProducto extends CrudRepository<Producto,Long>{
    
}
