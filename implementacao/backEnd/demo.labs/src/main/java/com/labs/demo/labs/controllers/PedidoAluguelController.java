package com.labs.demo.labs.controllers;

import com.labs.demo.labs.models.PedidoAluguel;
import com.labs.demo.labs.models.Cliente;
import com.labs.demo.labs.models.Automovel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labs.demo.labs.Enums.STATUS_PEDIDO;
import com.labs.demo.labs.services.AutomovelService;
import com.labs.demo.labs.services.PedidoAluguelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos-aluguel")
public class PedidoAluguelController {

    private final PedidoAluguelService pedidoAluguelService;
    private final AutomovelService automovelService;

    public PedidoAluguelController(PedidoAluguelService pedidoAluguelService, 
                                 AutomovelService automovelService) {
        this.pedidoAluguelService = pedidoAluguelService;
        this.automovelService = automovelService;
    }

    @JsonIgnore
    @PostMapping
    public ResponseEntity<PedidoAluguel> criarPedidoAluguel(
            @RequestParam String cpf,
            @RequestParam String automovelMatricula,
            @RequestParam float valor,
            @RequestParam int contratoDeCredito) {
        
        Automovel automovel = automovelService.buscarAutomovelPorMatricula(automovelMatricula)
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado"));
        
        if (!automovel.getDisponivel()) {
            throw new RuntimeException("O automóvel não está disponível para aluguel");
        }
        
        PedidoAluguel novoPedido = pedidoAluguelService.criarPedidoAluguel(
                cpf, automovelMatricula, valor, contratoDeCredito);
        
        automovel.setDisponivel(false);
        automovelService.atualizarAutomovel(automovelMatricula, automovel);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<PedidoAluguel>> listarTodosPedidos() {
        List<PedidoAluguel> pedidos = pedidoAluguelService.listarTodosPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoAluguel> buscarPedidoPorId(@PathVariable int id) {
        Optional<PedidoAluguel> pedido = pedidoAluguelService.buscarPedidoPorId(id);
        return pedido.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<PedidoAluguel>> buscarPedidosPorCliente(@RequestBody Cliente cliente) {
        List<PedidoAluguel> pedidos = pedidoAluguelService.buscarPedidosPorCliente(cliente);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/automovel")
    public ResponseEntity<List<PedidoAluguel>> buscarPedidosPorAutomovel(@RequestBody Automovel automovel) {
        List<PedidoAluguel> pedidos = pedidoAluguelService.buscarPedidosPorAutomovel(automovel);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/status")
    public ResponseEntity<List<PedidoAluguel>> buscarPedidosPorStatus(@RequestParam STATUS_PEDIDO status) {
        List<PedidoAluguel> pedidos = pedidoAluguelService.buscarPedidosPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoAluguel> atualizarPedido(
            @PathVariable int id,
            @RequestBody PedidoAluguel pedidoAtualizado) {

        PedidoAluguel pedido = pedidoAluguelService.atualizarPedido(id, pedidoAtualizado);
        return ResponseEntity.ok(pedido);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoAluguel> atualizarStatusPedido(
            @PathVariable int id,
            @RequestParam STATUS_PEDIDO novoStatus) {

        PedidoAluguel pedido = pedidoAluguelService.atualizarStatusPedido(id, novoStatus);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable int id) {
        pedidoAluguelService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

}