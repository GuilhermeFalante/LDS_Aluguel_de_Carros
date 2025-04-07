package com.labs.demo.labs.models;

import com.labs.demo.labs.Enums.TIPO_DONO;
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
public class Automovel {

    @Id
    @Column(nullable = false, unique = true)
    private String matricula;

    @Column(nullable = false)
    private int ano;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean disponivel = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TIPO_DONO tipoDono = TIPO_DONO.CLIENTE;
}