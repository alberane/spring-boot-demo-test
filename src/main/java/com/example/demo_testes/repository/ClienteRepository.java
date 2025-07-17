package com.example.demo_testes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo_testes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

  List<Cliente> findByNomeContainingIgnoreCase(String nome);

  @Query("SELECT c FROM Cliente c WHERE c.idade BETWEEN :min AND :max")
  List<Cliente> findByIdadeRange(@Param("min") Integer min, @Param("max") Integer max);

  boolean existsByEmail(String email);
}