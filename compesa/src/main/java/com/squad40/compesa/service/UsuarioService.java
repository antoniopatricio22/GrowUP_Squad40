package com.squad40.compesa.service;

import com.squad40.compesa.model.Usuario;
import com.squad40.compesa.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

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

    public Usuario createUsuario(Usuario usuario) {
        if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario updatedUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setUsername(updatedUsuario.getUsername());

        if (updatedUsuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(updatedUsuario.getPassword()));
        }

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
