package com.squad40.compesa.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Controlador extends Usuario {

    @OneToMany(mappedBy = "assignedTo")
    private List<Atividade> atividades;

    @OneToMany(mappedBy = "controlador")
    private List<Turno> turnos;

    public void updateStatusAtividade() {
        // TODO Update status
    }

    public void registerAtividade() {
        // TODO registrar Atividade
    }

    public void generateDailyRelatorio() {
        // TODO Gerar relatorio diario??
    }
}
