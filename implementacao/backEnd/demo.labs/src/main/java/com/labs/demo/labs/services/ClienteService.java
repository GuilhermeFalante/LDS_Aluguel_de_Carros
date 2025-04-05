package com.labs.demo.labs.services;

import com.labs.demo.labs.models.Cliente;
import com.labs.demo.labs.models.Emprego;
import com.labs.demo.labs.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Cliente criarCliente(Cliente cliente) {
        if (cliente.getEmpregos() != null && cliente.getEmpregos().size() > cliente.getLimRendimentos()) {
            throw new IllegalArgumentException("Número de empregos excede o limite permitido");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente atualizarDadosCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    if (clienteAtualizado.getRg() != null) {
                        cliente.setRg(clienteAtualizado.getRg());
                    }
                    if (clienteAtualizado.getEndereco() != null) {
                        cliente.setEndereco(clienteAtualizado.getEndereco());
                    }
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));
    }

    @Transactional
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Transactional
    public Cliente adicionarEmprego(Long id, Emprego emprego) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));

        if (cliente.getEmpregos().size() >= cliente.getLimRendimentos()) {
            throw new IllegalArgumentException("Limite máximo de empregos atingido para este cliente");
        }

        cliente.getEmpregos().add(emprego);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente removerEmprego(Long id, Long empregoId) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));

        cliente.getEmpregos().removeIf(emprego -> emprego.getId().equals(empregoId));
        return clienteRepository.save(cliente);
    }

    public List<Emprego> listarEmpregosDoCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));

        return cliente.getEmpregos();
    }
}