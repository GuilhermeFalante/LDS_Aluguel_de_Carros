package com.labs.demo.labs.repositories;

import com.labs.demo.labs.models.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRg(String rg);
    Optional<Cliente> findByCpf(String cpf);
}