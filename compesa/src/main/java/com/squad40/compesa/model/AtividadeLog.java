package com.squad40.compesa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AtividadeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario user;

    @Enumerated(EnumType.STRING)
    private Operacao operacao;

    private String entidade;
    private LocalDateTime timestamp;
    private String detalhes;

    public AtividadeLog() {
        this.timestamp = LocalDateTime.now();
    }

    public void logAtividade() {
        //TODO Detalhe das Atividades
    }
}
