package com.squad40.compesa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad40.compesa.repository.TurnoRepository;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    public void registerEntrada(Long controladorId) {
        // TODO Registrar Entrada
    }

    public void registerSaida(Long controladorId) {
        // TODO Registrar Saida
    }
}
