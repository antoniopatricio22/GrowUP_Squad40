package com.squad40.compesa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.squad40.compesa.model.Atividade;
import com.squad40.compesa.service.AtividadeService;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @GetMapping
    public List<Atividade> getAllAtividades() {
        return atividadeService.findAll();
    }

    @PostMapping
    public Atividade createAtividade(@RequestBody Atividade atividade) {
        return atividadeService.save(atividade);
    }

    @PutMapping("/{id}")
    public Atividade updateAtividade(@PathVariable Long id, @RequestBody Atividade atividade) {
        return atividadeService.update(id, atividade);
    }

    @DeleteMapping("/{id}")
    public void deleteAtividade(@PathVariable Long id) {
        atividadeService.delete(id);
    }
}
