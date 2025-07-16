package com.example.demo_testes.repository;

import com.example.demo_testes.model.Produto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProdutoRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProdutoRepository produtoRepository;

  private Produto produto1;
  private Produto produto2;

  @BeforeEach
  void setUp() {
    produto1 = new Produto(null, "Pizza Margherita", 25.90, "Pizza tradicional");
    produto2 = new Produto(null, "Pizza Calabresa", 28.90, "Pizza com calabresa");

    entityManager.persistAndFlush(produto1);
    entityManager.persistAndFlush(produto2);
  }

  @Test
  @DisplayName("Deve encontrar produtos por nome")
  void deveEncontrarProdutosPorNome() {
    // Act
    List<Produto> resultado = produtoRepository.findByNomeContainingIgnoreCase("pizza");

    // Assert
    assertEquals(2, resultado.size());
    assertTrue(resultado.stream().allMatch(p -> p.getNome().toLowerCase().contains("pizza")));
  }

  @Test
  @DisplayName("Deve encontrar produtos por faixa de preço")
  void deveEncontrarProdutosPorFaixaDePreco() {
    // Act
    List<Produto> resultado = produtoRepository.findByPrecoRange(25.0, 30.0);

    // Assert
    assertEquals(2, resultado.size());
    assertTrue(resultado.stream().allMatch(p -> p.getPreco() >= 25.0 && p.getPreco() <= 30.0));
  }

  @Test
  @DisplayName("Deve salvar produto corretamente")
  void deveSalvarProdutoCorretamente() {
    // Arrange
    Produto novoProduto = new Produto(null, "Hambúrguer", 15.90, "Hambúrguer artesanal");

    // Act
    Produto produtoSalvo = produtoRepository.save(novoProduto);

    // Assert
    assertNotNull(produtoSalvo.getId());
    assertEquals("Hambúrguer", produtoSalvo.getNome());

    Produto produtoEncontrado = entityManager.find(Produto.class, produtoSalvo.getId());
    assertNotNull(produtoEncontrado);
    assertEquals("Hambúrguer", produtoEncontrado.getNome());
  }
}