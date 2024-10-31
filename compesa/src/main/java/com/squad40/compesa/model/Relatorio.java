package com.squad40.compesa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario user;

    private LocalDate data;

    @OneToMany
    private List<AtividadeLog> atividades;

    public void generatePDF() {
        // TODO Gerar PDF
    }

    public void exportRelatorio() {
        // TODO Exportar relatorio
    }
}

