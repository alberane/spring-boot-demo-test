package com.example.demo_testes.service;

import com.example.demo_testes.model.Produto;
import com.example.demo_testes.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

  private final ProdutoRepository produtoRepository;

  public Produto salvar(Produto produto) {
    if (!produto.isValido()) {
      throw new IllegalArgumentException("Produto inválido");
    }
    return produtoRepository.save(produto);
  }

  public List<Produto> buscarTodos() {
    return produtoRepository.findAll();
  }

  public Optional<Produto> buscarPorId(Long id) {
    return produtoRepository.findById(id);
  }

  public List<Produto> buscarPorNome(String nome) {
    return produtoRepository.findByNomeContainingIgnoreCase(nome);
  }

  public void deletar(Long id) {
    if (!produtoRepository.existsById(id)) {
      throw new IllegalArgumentException("Produto não encontrado");
    }
    produtoRepository.deleteById(id);
  }
}