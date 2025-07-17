package com.example.demo_testes.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

  @Test
  void produtoValidoDeveRetornarTrue() {
    Produto produto = new Produto(1L, "Café", 10.0, "Café especial");
    assertTrue(produto.isValido());
  }

  @Test
  void produtoComNomeVazioDeveRetornarFalse() {
    Produto produto = new Produto(2L, "   ", 10.0, "Descrição");
    assertFalse(produto.isValido());
  }

  @Test
  void produtoComPrecoZeroDeveRetornarFalse() {
    Produto produto = new Produto(3L, "Café", 0.0, "Descrição");
    assertFalse(produto.isValido());
  }

  @Test
  void produtoComPrecoNegativoDeveRetornarFalse() {
    Produto produto = new Produto(4L, "Café", -5.0, "Descrição");
    assertFalse(produto.isValido());
  }

  @Test
  void produtoComNomeNuloDeveRetornarFalse() {
    Produto produto = new Produto(5L, null, 10.0, "Descrição");
    assertFalse(produto.isValido());
  }

  @Test
  void produtoComPrecoNuloDeveRetornarFalse() {
    Produto produto = new Produto(6L, "Café", null, "Descrição");
    assertFalse(produto.isValido());
  }
}