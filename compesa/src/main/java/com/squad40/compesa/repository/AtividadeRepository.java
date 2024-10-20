package com.squad40.compesa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.squad40.compesa.model.Atividade;
import com.squad40.compesa.model.Controlador;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByAssignedTo(Controlador controlador);
}

