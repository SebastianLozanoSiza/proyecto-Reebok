package com.sebas.demo.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sebas.demo.repositories.entities.Cliente;

public interface RepositoryCliente extends CrudRepository<Cliente,Long>{

    Optional<Cliente> findByEmail(String email);

    Cliente findByCedula(String cedula);
}
