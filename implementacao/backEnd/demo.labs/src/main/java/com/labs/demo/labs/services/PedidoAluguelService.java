package com.labs.demo.labs.services;

import com.labs.demo.labs.models.PedidoAluguel;
import com.labs.demo.labs.models.Cliente;
import com.labs.demo.labs.models.Automovel;
import com.labs.demo.labs.repositories.AutomovelRepository;
import com.labs.demo.labs.repositories.ClienteRepository;
import com.labs.demo.labs.repositories.PedidoAluguelRepository;
import com.labs.demo.labs.Enums.STATUS_PEDIDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoAluguelService {
    private final PedidoAluguelRepository pedidoAluguelRepository;
    private final ClienteRepository clienteRepository;
    private final AutomovelRepository automovelRepository;

    public PedidoAluguelService(PedidoAluguelRepository pedidoAluguelRepository, ClienteRepository clienteRepository, AutomovelRepository automovelRepository) {
        this.pedidoAluguelRepository = pedidoAluguelRepository;
        this.clienteRepository = clienteRepository;
        this.automovelRepository = automovelRepository;
    }

    @Transactional
    public PedidoAluguel criarPedidoAluguel(String cpf, String automovelMatricula, float valor,
            int contratoDeCredito) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + cpf));

        Automovel automovel = automovelRepository.findById(automovelMatricula)
                .orElseThrow(
                        () -> new RuntimeException("Automóvel não encontrado com a matrícula: " + automovelMatricula));

        PedidoAluguel pedidoAluguel = new PedidoAluguel();
        pedidoAluguel.setCliente(cliente);
        pedidoAluguel.setAutomovel(automovel);
        pedidoAluguel.setValor(valor);
        pedidoAluguel.setContratoDeCredito(contratoDeCredito);
        pedidoAluguel.setStatus(STATUS_PEDIDO.ABERTO);

        return pedidoAluguelRepository.save(pedidoAluguel);
    }

    public List<PedidoAluguel> listarTodosPedidos() {
        return pedidoAluguelRepository.findAll();
    }

    public Optional<PedidoAluguel> buscarPedidoPorId(int id) {
        return pedidoAluguelRepository.findById(id);
    }

    public List<PedidoAluguel> buscarPedidosPorCliente(Cliente cliente) {
        return pedidoAluguelRepository.findByCliente(cliente);
    }

    public List<PedidoAluguel> buscarPedidosPorAutomovel(Automovel automovel) {
        return pedidoAluguelRepository.findByAutomovel(automovel);
    }

    public List<PedidoAluguel> buscarPedidosPorStatus(STATUS_PEDIDO status) {
        return pedidoAluguelRepository.findByStatus(status);
    }

    @Transactional
    public PedidoAluguel atualizarPedido(int id, PedidoAluguel pedidoAtualizado) {
        return pedidoAluguelRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(pedidoAtualizado.getStatus());
                    pedido.setValor(pedidoAtualizado.getValor());
                    pedido.setContratoDeCredito(pedidoAtualizado.getContratoDeCredito());
                    return pedidoAluguelRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido de aluguel não encontrado com o ID: " + id));
    }

    @Transactional
    public PedidoAluguel atualizarStatusPedido(int id, STATUS_PEDIDO novoStatus) {
        return pedidoAluguelRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(novoStatus);
                    return pedidoAluguelRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido de aluguel não encontrado com o ID: " + id));
    }

    @Transactional
    public void deletarPedido(int id) {
        pedidoAluguelRepository.deleteById(id);
    }

}