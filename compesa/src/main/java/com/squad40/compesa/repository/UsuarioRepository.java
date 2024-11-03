package com.squad40.compesa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.squad40.compesa.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
