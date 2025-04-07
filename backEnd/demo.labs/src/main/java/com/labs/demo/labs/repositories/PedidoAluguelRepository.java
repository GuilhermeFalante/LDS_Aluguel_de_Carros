package com.labs.demo.labs.repositories;

import com.labs.demo.labs.models.Automovel;
import com.labs.demo.labs.models.Cliente;
import com.labs.demo.labs.models.PedidoAluguel;
import com.labs.demo.labs.Enums.STATUS_PEDIDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, Integer> {
    List<PedidoAluguel> findByStatus(STATUS_PEDIDO status);

    List<PedidoAluguel> findByCliente(Cliente cliente);

    List<PedidoAluguel> findByAutomovel(Automovel automovel);

    List<PedidoAluguel> findByValorGreaterThan(float valor);
    
    List<PedidoAluguel> findByValorLessThan(float valor);
}