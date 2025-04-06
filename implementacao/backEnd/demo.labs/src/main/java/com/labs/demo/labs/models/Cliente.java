package com.labs.demo.labs.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario{
    
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String rg;

    @Column(nullable = false)
    private String endereco;

    public final int LimRendimentos = 3;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprego> empregos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoAluguel> pedidosAluguel = new ArrayList<>();

}