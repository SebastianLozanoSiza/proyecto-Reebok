package com.sebas.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sebas.demo.repositories.entities.Transaccion;

public interface RepositoryTransaccion extends CrudRepository<Transaccion, Long> {
    
}
