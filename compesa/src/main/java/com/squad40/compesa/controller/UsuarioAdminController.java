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

// UsuarioAdminController.java

@RestController
@RequestMapping("/admin")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/usuarios")
    public ResponseEntity<?> createUsuario(@RequestHeader("Authorization") String token,
                                           @RequestBody Map<String, Object> usuarioData) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        String username = (String) usuarioData.get("username");
        String password = (String) usuarioData.get("password");
        Role role = Role.valueOf((String) usuarioData.get("role"));

        Usuario usuario;
        if (role == Role.ADMINISTRADOR) {
            usuario = new Administrador();
        } else {
            usuario = new Controlador();
        }

        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRole(role);

        Usuario novoUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> updateUsuario(@RequestHeader("Authorization") String token,
                                       @PathVariable("id") Long id, @RequestBody Map<String, Object> usuarioData) {
    if (!isAdmin(token)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
    }
    
    // Extrair dados do usuário a partir do Map
    String username = (String) usuarioData.get("username");
    String password = (String) usuarioData.get("password");
    String roleString = (String) usuarioData.get("role");
    Role role = Role.valueOf(roleString);

    // Verifica o tipo de usuário e cria uma instância da subclasse apropriada da pra usa instanceof???
    Usuario usuario;
    if (role == Role.ADMINISTRADOR) {
        usuario = new Administrador();
    } else {
        usuario = new Controlador();
    }

    // Configura os dados do usuário atualizado
    usuario.setUsername(username);
    usuario.setPassword(password);
    usuario.setRole(role);

    // Atualiza o usuário usando o serviço
    Usuario usuarioAtualizado = usuarioService.updateUsuario(id, usuario);
    return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> deleteUsuario(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok("Usuário removido com sucesso.");
    }

    // Método para listar todos os usuários
    @GetMapping("/usuarios")
    public ResponseEntity<?> listarUsuarios(@RequestHeader("Authorization") String token) {
    if (!isAdmin(token)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
    }
    return ResponseEntity.ok(usuarioService.listarTodosUsuarios());
}

    // Método para buscar um usuário específico pelo ID
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
    if (!isAdmin(token)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.1");
    }
    Usuario usuario = usuarioService.buscarUsuarioPorId(id);
    return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
}


    // Método auxiliar para verificar se o usuário autenticado é um ADMINISTRADOR
    private boolean isAdmin(String token) {
        String jwt = token.substring(7); // Remove o prefixo "Bearer "
        String username = jwtTokenService.getUsernameFromToken(jwt);
        String role = jwtTokenService.getRoleFromToken(jwt);
        return username != null && "ROLE_ADMINISTRADOR".equals(role);
    }

}
