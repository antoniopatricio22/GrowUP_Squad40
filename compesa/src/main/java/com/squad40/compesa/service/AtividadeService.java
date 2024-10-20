package com.squad40.compesa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad40.compesa.model.Atividade;
import com.squad40.compesa.repository.AtividadeRepository;

import java.util.List;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    public List<Atividade> findAll() {
        return atividadeRepository.findAll();
    }

    public Atividade save(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public Atividade update(Long id, Atividade newAtividade) {
        Atividade atividade = atividadeRepository.findById(id).orElseThrow();
        atividade.setDescricao(newAtividade.getDescricao()); //Depende de Implementar o método em Atividade
        atividade.setStatus(newAtividade.getStatus()); //Depende de Implementar o método em Atividade
        return atividadeRepository.save(atividade);
    }

    public void delete(Long id) {
        atividadeRepository.deleteById(id);
    }
}
