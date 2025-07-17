package com.example.demo_testes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_testes.model.Cliente;
import com.example.demo_testes.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @GetMapping
  public List<Cliente> buscarTodos() {
    return clienteService.buscarTodos();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
    return clienteService.buscarPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
    try {
      Cliente clienteSalvo = clienteService.salvar(cliente);
      return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}