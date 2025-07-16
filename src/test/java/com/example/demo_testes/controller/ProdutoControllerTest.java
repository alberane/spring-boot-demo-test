package com.example.demo_testes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo_testes.model.Produto;
import com.example.demo_testes.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SuppressWarnings("removal")
  @MockBean
  private ProdutoService produtoService;

  @Autowired
  private ObjectMapper objectMapper;

  private Produto produto;

  @BeforeEach
  void setUp() {
    produto = new Produto(1L, "Pizza Margherita", 25.90, "Pizza tradicional");
  }

  @Test
  @DisplayName("Deve buscar todos os produtos")
  void deveBuscarTodosOsProdutos() throws Exception {
    // Arrange
    when(produtoService.buscarTodos()).thenReturn(Arrays.asList(produto));

    // Act & Assert
    mockMvc.perform(get("/api/produtos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].nome").value("Pizza Margherita"))
        .andExpect(jsonPath("$[0].preco").value(25.90));

    verify(produtoService, times(1)).buscarTodos();
  }

  @Test
  @DisplayName("Deve buscar produto por ID")
  void deveBuscarProdutoPorId() throws Exception {
    // Arrange
    when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));

    // Act & Assert
    mockMvc.perform(get("/api/produtos/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Pizza Margherita"))
        .andExpect(jsonPath("$.preco").value(25.90));

    verify(produtoService, times(1)).buscarPorId(1L);
  }

  @Test
  @DisplayName("Deve retornar 404 para produto não encontrado")
  void deveRetornar404ParaProdutoNaoEncontrado() throws Exception {
    // Arrange
    when(produtoService.buscarPorId(anyLong())).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(get("/api/produtos/999"))
        .andExpect(status().isNotFound());

    verify(produtoService, times(1)).buscarPorId(999L);
  }

  @Test
  @DisplayName("Deve criar produto válido")
  void deveCriarProdutoValido() throws Exception {
    // Arrange
    Produto novoProduto = new Produto(null, "Hambúrguer", 15.90, "Hambúrguer artesanal");
    when(produtoService.salvar(any(Produto.class))).thenReturn(produto);

    // Act & Assert
    mockMvc.perform(post("/api/produtos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(novoProduto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Pizza Margherita"));

    verify(produtoService, times(1)).salvar(any(Produto.class));
  }

  @Test
  @DisplayName("Deve retornar 400 para produto inválido")
  void deveRetornar400ParaProdutoInvalido() throws Exception {
    // Arrange
    Produto produtoInvalido = new Produto(null, "", -10.0, "");
    when(produtoService.salvar(any(Produto.class))).thenThrow(new IllegalArgumentException("Produto inválido"));

    // Act & Assert
    mockMvc.perform(post("/api/produtos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(produtoInvalido)))
        .andExpect(status().isBadRequest());

    verify(produtoService, times(1)).salvar(any(Produto.class));
  }

  @Test
  @DisplayName("Deve deletar produto existente")
  void deveDeletarProdutoExistente() throws Exception {
    // Arrange
    doNothing().when(produtoService).deletar(1L);

    // Act & Assert
    mockMvc.perform(delete("/api/produtos/1"))
        .andExpect(status().isNoContent());

    verify(produtoService, times(1)).deletar(1L);
  }

  @Test
  @DisplayName("Deve retornar 404 ao deletar produto inexistente")
  void deveRetornar404AoDeletarProdutoInexistente() throws Exception {
    // Arrange
    doThrow(new IllegalArgumentException("Produto não encontrado")).when(produtoService).deletar(anyLong());

    // Act & Assert
    mockMvc.perform(delete("/api/produtos/999"))
        .andExpect(status().isNotFound());

    verify(produtoService, times(1)).deletar(999L);
  }
}