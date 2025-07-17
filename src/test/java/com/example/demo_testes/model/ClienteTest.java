package com.example.demo_testes.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClienteTest {

  @Test
  void clienteComNomeValidoDeveRetornarTrue() {
    Cliente cliente = new Cliente();
    cliente.setNome("João");
    assertTrue(cliente.isValido());
  }

  @Test
  void clienteComNomeNuloDeveRetornarFalse() {
    Cliente cliente = new Cliente();
    cliente.setNome(null);
    assertFalse(cliente.isValido());
  }

  @Test
  void clienteComNomeVazioDeveRetornarFalse() {
    Cliente cliente = new Cliente();
    cliente.setNome("   ");
    assertFalse(cliente.isValido());
  }

  @Test
  void deveSetarEObterValoresCorretamente() {
    Cliente cliente = new Cliente();
    cliente.setId(10L);
    cliente.setNome("Ana");
    cliente.setEmail("ana@email.com");
    cliente.setIdade(22);

    assertEquals(10L, cliente.getId());
    assertEquals("Ana", cliente.getNome());
    assertEquals("ana@email.com", cliente.getEmail());
    assertEquals(22, cliente.getIdade());
  }

}
