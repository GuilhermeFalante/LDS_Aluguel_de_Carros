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
@CrossOrigin(origins = "*")
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

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable Long id,
            @RequestBody Cliente clienteAtualizado) {
        try {
            Cliente cliente = clienteService.atualizarDadosCliente(id, clienteAtualizado);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/empregos")
    public ResponseEntity<?> adicionarEmprego(
            @PathVariable Long id,
            @RequestBody Emprego emprego) {
        try {
            Cliente cliente = clienteService.adicionarEmprego(id, emprego);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/empregos/{empregoId}")
    public ResponseEntity<?> removerEmprego(
            @PathVariable Long id,
            @PathVariable Long empregoId) {
        try {
            Cliente cliente = clienteService.removerEmprego(id, empregoId);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/empregos")
    public ResponseEntity<?> listarEmpregosDoCliente(@PathVariable Long id) {
        try {
            List<Emprego> empregos = clienteService.listarEmpregosDoCliente(id);
            return ResponseEntity.ok(empregos);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}