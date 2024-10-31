package com.squad40.compesa.controller;

import com.squad40.compesa.model.Usuario;
import com.squad40.compesa.service.JwtTokenService;
import com.squad40.compesa.service.UsuarioService;
import com.squad40.compesa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
    Usuario usuario = usuarioRepository.findByUsername(credentials.get("username"));
    if (usuario != null && usuarioService.validatePassword(credentials.get("password"), usuario.getPassword())) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"))
        );

        String token = jwtTokenService.generateToken(authentication);
        return Map.of("token", token);
    } else {
        return Map.of("error", "Invalid username or password");
    }
}
    
}
