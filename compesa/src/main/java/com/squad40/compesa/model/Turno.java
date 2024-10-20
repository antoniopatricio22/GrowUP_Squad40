package com.squad40.compesa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Controlador controlador;

    private LocalDateTime entrada;
    private LocalDateTime saida;

    public void registerEntrada() {
        this.entrada = LocalDateTime.now();
    }

    public void registerSaida() {
        this.saida = LocalDateTime.now();
    }
}
