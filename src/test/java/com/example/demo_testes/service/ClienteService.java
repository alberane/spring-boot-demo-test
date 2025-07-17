package com.example.demo_testes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo_testes.model.Cliente;
import com.example.demo_testes.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

  @Mock
  ClienteRepository clienteRepository;

  @InjectMocks
  ClienteService clienteService;

  @Test
  void salvarClienteValidoDeveSalvar() {
    Cliente cliente = new Cliente(1L, "Maria", "maria@email.com", 30);
    when(clienteRepository.save(cliente)).thenReturn(cliente);

    Cliente salvo = clienteService.salvar(cliente);

    assertEquals(cliente, salvo);
    verify(clienteRepository).save(cliente);
  }

  @Test
  void salvarClienteInvalidoDeveLancarExcecao() {
    Cliente cliente = new Cliente(1L, "   ", "email@email.com", 20);

    assertThrows(IllegalArgumentException.class, () -> clienteService.salvar(cliente));
    verify(clienteRepository, never()).save(any());
  }

  @Test
  void buscarTodosDeveRetornarLista() {
    List<Cliente> lista = Arrays.asList(
        new Cliente(1L, "Ana", "ana@email.com", 22),
        new Cliente(2L, "João", "joao@email.com", 25));
    when(clienteRepository.findAll()).thenReturn(lista);

    List<Cliente> resultado = clienteService.buscarTodos();

    assertEquals(2, resultado.size());
    assertEquals("Ana", resultado.get(0).getNome());
  }

  @Test
  void buscarPorIdDeveRetornarOptional() {
    Cliente cliente = new Cliente(1L, "Ana", "ana@email.com", 22);
    when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

    Optional<Cliente> resultado = clienteService.buscarPorId(1L);

    assertTrue(resultado.isPresent());
    assertEquals("Ana", resultado.get().getNome());
  }

  @Test
  void buscarPorNomeDeveRetornarLista() {
    List<Cliente> lista = Arrays.asList(
        new Cliente(1L, "Ana", "ana@email.com", 22));
    when(clienteRepository.findByNomeContainingIgnoreCase("Ana")).thenReturn(lista);

    List<Cliente> resultado = clienteService.buscarPorNome("Ana");

    assertEquals(1, resultado.size());
    assertEquals("Ana", resultado.get(0).getNome());
  }
}
