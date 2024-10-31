package com.squad40.compesa.service;

import com.squad40.compesa.model.Administrador;
import com.squad40.compesa.model.Controlador;
import com.squad40.compesa.model.Role;
import com.squad40.compesa.model.Usuario;
import com.squad40.compesa.repository.UsuarioRepository;


import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService, CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRole().name())
                .build();
    }

    
    public Usuario save(Usuario usuario) {
        usuario.setPassword(encryptPassword(usuario.getPassword())); // Encrypt password when saving a new user???
        return usuarioRepository.save(usuario);
    }

    // Encripta password
    public String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    // Valida password
    public boolean validatePassword(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    // Adiciona o método run para executar no início da aplicação
    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByUsername("admin") == null) {
            Usuario admin = new Administrador();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMINISTRADOR);
            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByUsername("controlador") == null) {
            Usuario controlador = new Controlador();
            controlador.setUsername("controlador");
            controlador.setPassword(passwordEncoder.encode("controlador123"));
            controlador.setRole(Role.CONTROLADOR);
            usuarioRepository.save(controlador);
        }
        
    }

    //----------------------------------------------------------//

    public Usuario createUsuario(Usuario usuario) {
        usuario.setPassword(encryptPassword(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario updatedUsuario) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setUsername(updatedUsuario.getUsername());
        usuario.setPassword(encryptPassword(updatedUsuario.getPassword()));
        usuario.setRole(updatedUsuario.getRole());
        usuario.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método para listar todos os usuários
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para buscar usuário por username
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Método para buscar um usuário específico pelo ID
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
