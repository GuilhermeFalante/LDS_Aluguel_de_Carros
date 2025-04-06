package com.labs.demo.labs.models;

import com.labs.demo.labs.Enums.STATUS_PEDIDO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoAluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS_PEDIDO status;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = true, columnDefinition = "double default 0")
    private double contratoDeCredito;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "automovel_matricula")
    private Automovel automovel;
}