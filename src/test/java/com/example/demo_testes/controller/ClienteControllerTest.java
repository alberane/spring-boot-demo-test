package com.example.demo_testes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo_testes.model.Cliente;
import com.example.demo_testes.service.ClienteService;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SuppressWarnings("removal")
  @MockBean
  private ClienteService clienteService;

  @Test
  void buscarTodosDeveRetornarListaDeClientes() throws Exception {
    Cliente cliente1 = new Cliente(1L, "Ana", "ana@email.com", 22);
    Cliente cliente2 = new Cliente(2L, "João", "joao@email.com", 25);

    Mockito.when(clienteService.buscarTodos()).thenReturn(Arrays.asList(cliente1, cliente2));

    mockMvc.perform(get("/api/clientes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nome").value("Ana"))
        .andExpect(jsonPath("$[1].email").value("joao@email.com"));
  }

  @Test
  void buscarPorIdDeveRetornarCliente() throws Exception {
    Cliente cliente = new Cliente(1L, "Ana", "ana@site.com", 22);

    Mockito.when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

    mockMvc.perform(get("/api/clientes/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Ana"))
        .andExpect(jsonPath("$.email").value("ana@site.com"))
        .andExpect(jsonPath("$.idade").value(22));
  }

  @Test
  void salvarClienteValidoDeveSalvar() throws Exception {
    Cliente clienteSalvo = new Cliente(1L, "Ana", "ana@site.com", 22);

    Mockito.when(clienteService.salvar(any(Cliente.class))).thenReturn(clienteSalvo);

    mockMvc.perform(post("/api/clientes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"nome\":\"Ana\", \"email\":\"ana@site.com\", \"idade\":22}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.nome").value("Ana"))
        .andExpect(jsonPath("$.email").value("ana@site.com"))
        .andExpect(jsonPath("$.idade").value(22));
  }

  @Test
  void salvarClienteInvalidoDeveRetornarErro() throws Exception {
    Mockito.when(clienteService.salvar(any(Cliente.class)))
        .thenThrow(new IllegalArgumentException());

    mockMvc.perform(post("/api/clientes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"nome\":\"\", \"email\":\"\", \"idade\":-1}"))
        .andExpect(status().isBadRequest());
  }

}