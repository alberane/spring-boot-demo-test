package com.example.demo_testes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo_testes.model.Cliente;
import com.example.demo_testes.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final ClienteRepository clienteRepository;

  public Cliente salvar(Cliente cliente) {
    if (!cliente.isValido()) {
      throw new IllegalArgumentException("Cliente inválido");
    }
    return clienteRepository.save(cliente);
  }

  public List<Cliente> buscarTodos() {
    return clienteRepository.findAll();
  }

  public Optional<Cliente> buscarPorId(Long id) {
    return clienteRepository.findById(id);
  }

  public List<Cliente> buscarPorNome(String nome) {
    return clienteRepository.findByNomeContainingIgnoreCase(nome);
  }

}
