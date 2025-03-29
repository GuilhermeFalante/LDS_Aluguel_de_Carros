package com.labs.demo.labs.controllers;

import com.labs.demo.labs.models.Cliente;
import com.labs.demo.labs.models.Emprego;
import com.labs.demo.labs.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
           return clienteService.criarCliente(cliente);
    }

    @GetMapping
    public List<Cliente> listarTodosClientes() {
        return clienteService.listarTodosClientes();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        Optional<Cliente> cliente = clienteService.buscarPorCpf(cpf);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable String cpf,
            @RequestBody Cliente clienteAtualizado) {
        try {
            Cliente cliente = clienteService.atualizarCliente(cpf, clienteAtualizado);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf) {
        try {
            clienteService.deletarCliente(cpf);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cpf}/empregos")
    public ResponseEntity<?> adicionarEmprego(
            @PathVariable String cpf,
            @RequestBody Emprego emprego) {
        try {
            Cliente cliente = clienteService.adicionarEmprego(cpf, emprego);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cpf}/empregos/{empregoId}")
    public ResponseEntity<?> removerEmprego(
            @PathVariable String cpf,
            @PathVariable Long empregoId) {
        try {
            Cliente cliente = clienteService.removerEmprego(cpf, empregoId);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{cpf}/empregos")
    public ResponseEntity<?> listarEmpregosDoCliente(@PathVariable String cpf) {
        try {
            List<Emprego> empregos = clienteService.listarEmpregosDoCliente(cpf);
            return ResponseEntity.ok(empregos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}