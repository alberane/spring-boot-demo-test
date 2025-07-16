package com.example.demo_testes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo_testes.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

  List<Produto> findByNomeContainingIgnoreCase(String nome);

  @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :min AND :max")
  List<Produto> findByPrecoRange(@Param("min") Double min, @Param("max") Double max);
}