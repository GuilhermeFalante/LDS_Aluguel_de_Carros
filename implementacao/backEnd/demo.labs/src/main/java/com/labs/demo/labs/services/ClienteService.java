package com.labs.demo.labs.services;

import com.labs.demo.labs.models.Cliente;
import com.labs.demo.labs.models.Emprego;
import com.labs.demo.labs.repositories.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
 
    @Autowired
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
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findById(cpf);
    }

    @Transactional
    public Cliente atualizarCliente(String cpf, Cliente clienteAtualizado) {
        return clienteRepository.findById(cpf)
                .map(cliente -> {
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    
                    if (clienteAtualizado.getEmpregos() != null) {
                        if (clienteAtualizado.getEmpregos().size() > cliente.getLimRendimentos()) {
                            throw new IllegalArgumentException("Número de empregos excede o limite permitido");
                        }
                        cliente.getEmpregos().clear();
                        cliente.getEmpregos().addAll(clienteAtualizado.getEmpregos());
                    }
                    
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
    }

    @Transactional
    public void deletarCliente(String cpf) {
        clienteRepository.deleteById(cpf);
    }

    @Transactional
    public Cliente adicionarEmprego(String cpf, Emprego emprego) {
        Cliente cliente = clienteRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
        
        if (cliente.getEmpregos().size() >= cliente.getLimRendimentos()) {
            throw new IllegalArgumentException("Limite máximo de empregos atingido para este cliente");
        }
        
        cliente.getEmpregos().add(emprego);
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente removerEmprego(String cpf, Long empregoId) {
        Cliente cliente = clienteRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
        
        cliente.getEmpregos().removeIf(emprego -> emprego.getId().equals(empregoId));
        return clienteRepository.save(cliente);
    }

    public List<Emprego> listarEmpregosDoCliente(String cpf) {
        Cliente cliente = clienteRepository.findById(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
        
        return cliente.getEmpregos();
    }
}