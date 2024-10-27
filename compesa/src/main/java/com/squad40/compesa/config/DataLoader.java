package com.squad40.compesa.config;

import com.squad40.compesa.model.Administrador;
import com.squad40.compesa.model.Controlador;
import com.squad40.compesa.model.Role;
//import com.squad40.compesa.model.Usuario;
import com.squad40.compesa.repository.UsuarioRepository;
import com.squad40.compesa.service.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
   
   
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordService passwordService; // Inject PasswordService

    @Override
    public void run(String... args) throws Exception {
        loadUsers();
    }



private void loadUsers() {
    // Criar e salvar um Administrador
    Administrador admin = new Administrador();
    admin.setUsername("admin");
    admin.setPassword(passwordService.encodePassword("admin123")); // Criptografando a senha
    admin.setRole(Role.ADMINISTRADOR);
    usuarioRepository.save(admin);

    // Criar e salvar um Controlador
    Controlador controlador = new Controlador();
    controlador.setUsername("controlador");
    controlador.setPassword(passwordService.encodePassword("controlador123")); // Criptografando a senha
    controlador.setRole(Role.CONTROLADOR);
    usuarioRepository.save(controlador);

}
}
