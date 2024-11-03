package com.squad40.compesa.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad40.compesa.model.Administrador;
import com.squad40.compesa.model.Controlador;
import com.squad40.compesa.model.Role;
import com.squad40.compesa.model.Usuario;
import com.squad40.compesa.service.JwtTokenService;
import com.squad40.compesa.service.UsuarioService;

import com.squad40.compesa.model.StandardResponse;


@RestController
@RequestMapping("/admin")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/usuarios")
    public ResponseEntity<StandardResponse<?>> createUsuario(@RequestHeader("Authorization") String token,
            @RequestBody Map<String, Object> usuarioData) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(StandardResponse.error("Acesso negado", "Usuário não tem permissão para criar contas"));
        }

        String username = (String) usuarioData.get("username");
        String password = (String) usuarioData.get("password");
        Role role = Role.valueOf((String) usuarioData.get("role"));

        Usuario usuario = role == Role.ADMINISTRADOR ? new Administrador() : new Controlador();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRole(role);

        Usuario novoUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.ok(StandardResponse.success("Usuário Criado com Sucesso", novoUsuario));
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<StandardResponse<?>> updateUsuario(@RequestHeader("Authorization") String token,
            @PathVariable("id") Long id, @RequestBody Map<String, Object> usuarioData) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(StandardResponse.error("Acesso negado.", "Usuário não tem permissão para criar contas"));
        }

        String username = (String) usuarioData.get("username");
        String password = (String) usuarioData.get("password");
        Role role = Role.valueOf((String) usuarioData.get("role"));

        try {
            Usuario updatedUsuario = role == Role.ADMINISTRADOR ? new Administrador() : new Controlador();
            updatedUsuario.setUsername(username);
            updatedUsuario.setPassword(password);
            updatedUsuario.setRole(role);

            Usuario usuarioAtualizado = usuarioService.updateUsuario(id, updatedUsuario);
            return ResponseEntity.ok(StandardResponse.success("Usuário Atualizado com Sucesso", usuarioAtualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(StandardResponse.error("Usuário não encontrado", e.getMessage()));
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<StandardResponse<?>> deleteUsuario(@RequestHeader("Authorization") String token,
            @PathVariable("id") Long id) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(StandardResponse.error("Acesso negado.", "Usuário não tem permissão para criar contas"));
        }

        if (usuarioService.buscarUsuarioPorId(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(StandardResponse.error("Usuário não encontrado", "Não Existe Usuário com Esse ID"));
        }

        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok(StandardResponse.success("Usuário Removido com Sucesso", null));

    }

    @GetMapping("/usuarios/{id}") // Método ainda a ser utilizado em conjunto com emissão de Relatórios.
    public ResponseEntity<StandardResponse<?>> buscarUsuarioPorId(@RequestHeader("Authorization") String token,
            @PathVariable("id") Long id) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(StandardResponse.error("Acesso negado.", "Usuário não tem permissão para criar contas"));
        }

        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            return ResponseEntity.ok(StandardResponse.success("Usuário Recuperado com Sucesso", usuario));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(StandardResponse.error("Usuário não encontrado", "Não Existe Usuário com Esse ID"));
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<StandardResponse<?>> listarUsuarios(@RequestHeader("Authorization") String token) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(StandardResponse.error("Acesso negado.", "Usuário não tem permissão para criar contas"));
        }

        return ResponseEntity
                .ok(StandardResponse.success("Lista de Usuários Recuperada com Sucesso",
                        usuarioService.listarTodosUsuarios()));
    }

    // Método auxiliar, será que vale a pena mover para UserService?
    private boolean isAdmin(String token) {
        String jwt = token.substring(7);
        String role = jwtTokenService.getRoleFromToken(jwt);
        return "ROLE_ADMINISTRADOR".equals(role);
    }
}
