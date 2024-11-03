package com.squad40.compesa.controller;

import com.squad40.compesa.model.Usuario;
import com.squad40.compesa.service.JwtTokenService;
import com.squad40.compesa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.squad40.compesa.model.StandardResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<StandardResponse<?>> login(@RequestBody Map<String, String> credentials) {
        Usuario usuario = usuarioService.buscarPorUsername(credentials.get("username"));
        if (usuario != null && usuarioService.validatePassword(credentials.get("password"), usuario.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password")));

            String token = jwtTokenService.generateToken(authentication);
            return ResponseEntity.ok(StandardResponse.success("Login Bem Sucedido", Map.of("token", token)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(StandardResponse.error("Login Falhou", "Invalid username or password"));
        }
    }
}
