package com.example.demo_testes.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo_testes.model.Produto;
import com.example.demo_testes.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

  @Mock
  private ProdutoRepository produtoRepository;

  @InjectMocks
  private ProdutoService produtoService;

  private Produto produto;

  @BeforeEach
  void setUp() {
    produto = new Produto(1L, "Pizza Margherita", 25.90, "Pizza com molho de tomate e mussarela");
  }

  @Test
  @DisplayName("Deve salvar produto válido")
  void deveSalvarProdutoValido() {
    // Arrange
    when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

    // Act
    Produto resultado = produtoService.salvar(produto);

    // Assert
    assertNotNull(resultado);
    assertEquals("Pizza Margherita", resultado.getNome());
    assertEquals(25.90, resultado.getPreco());
    verify(produtoRepository, times(1)).save(produto);
  }

  @Test
  @DisplayName("Deve lançar exceção para produto inválido")
  void deveLancarExcecaoParaProdutoInvalido() {
    // Arrange
    Produto produtoInvalido = new Produto(null, "", -10.0, "");

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> produtoService.salvar(produtoInvalido));

    assertEquals("Produto inválido", exception.getMessage());
    verify(produtoRepository, never()).save(any());
  }

  @Test
  @DisplayName("Deve buscar todos os produtos")
  void deveBuscarTodosOsProdutos() {
    // Arrange
    List<Produto> produtos = Arrays.asList(
        new Produto(1L, "Pizza", 20.0, "Descrição"),
        new Produto(2L, "Hambúrguer", 15.0, "Descrição"));
    when(produtoRepository.findAll()).thenReturn(produtos);

    // Act
    List<Produto> resultado = produtoService.buscarTodos();

    // Assert
    assertEquals(2, resultado.size());
    verify(produtoRepository, times(1)).findAll();
  }

  // @Test
  // @DisplayName("Deve buscar um produto por nome")
  // void deveBuscarUmProdutoPorNome() {
  // // Arrange
  // List<Produto> produtos = Arrays.asList(
  // new Produto(1L, "Pizza", 20.0, "Descrição"),
  // new Produto(2L, "Refri", 20.0, "Descrição Refri"));
  // when(produtoRepository.findByNomeContainingIgnoreCase("Pizza")).thenReturn(produtos);

  // // Act
  // List<Produto> resultado = produtoService.buscarPorNome("Pizza");

  // // Assert
  // assertEquals(2, resultado.size());
  // verify(produtoRepository, times(1)).findByNomeContainingIgnoreCase("Pizza");
  // }

  @Test
  @DisplayName("Deve buscar produto por ID")
  void deveBuscarProdutoPorId() {
    // Arrange
    when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

    // Act
    Optional<Produto> resultado = produtoService.buscarPorId(1L);

    // Assert
    assertTrue(resultado.isPresent());
    assertEquals("Pizza Margherita", resultado.get().getNome());
    verify(produtoRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Deve deletar produto existente")
  void deveDeletarProdutoExistente() {
    // Arrange
    when(produtoRepository.existsById(1L)).thenReturn(true);

    // Act
    assertDoesNotThrow(() -> produtoService.deletar(1L));

    // Assert
    verify(produtoRepository, times(1)).existsById(1L);
    verify(produtoRepository, times(1)).deleteById(1L);
  }

  @Test
  @DisplayName("Deve lançar exceção ao deletar produto inexistente")
  void deveLancarExcecaoAoDeletarProdutoInexistente() {
    // Arrange
    when(produtoRepository.existsById(anyLong())).thenReturn(false);

    // Act & Assert
    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        () -> produtoService.deletar(1L));

    assertEquals("Produto não encontrado", exception.getMessage());
    verify(produtoRepository, never()).deleteById(anyLong());
  }
}