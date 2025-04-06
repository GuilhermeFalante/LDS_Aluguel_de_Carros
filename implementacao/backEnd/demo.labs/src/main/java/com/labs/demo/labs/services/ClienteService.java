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
        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente com este CPF");
        }
        
        if (cliente.getEmpregos() != null && cliente.getEmpregos().size() > cliente.getLimRendimentos()) {
            throw new IllegalArgumentException("Número de empregos excede o limite permitido");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente atualizarDadosCliente(String cpf, Cliente clienteAtualizado) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o CPF: " + cpf));

        if (clienteAtualizado.getRg() != null) {
            cliente.setRg(clienteAtualizado.getRg());
        }
        if (clienteAtualizado.getEndereco() != null) {
            cliente.setEndereco(clienteAtualizado.getEndereco());
        }
        
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void deletarCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o CPF: " + cpf));
        clienteRepository.delete(cliente);
    }

    @Transactional
    public Cliente adicionarEmprego(String cpf, Emprego emprego) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o cpf: " + cpf));

        if (cliente.getEmpregos().size() >= cliente.getLimRendimentos()) {
            throw new IllegalArgumentException("Limite máximo de empregos atingido para este cliente");
        }

        cliente.getEmpregos().add(emprego);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente removerEmprego(String cpf, Long empregoId) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o cpf: " + cpf));

        cliente.getEmpregos().removeIf(emprego -> emprego.getId().equals(empregoId));
        return clienteRepository.save(cliente);
    }

    public List<Emprego> listarEmpregosDoCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o cpf: " + cpf));

        return cliente.getEmpregos();
    }
}