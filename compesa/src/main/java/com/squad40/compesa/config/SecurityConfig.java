package com.squad40.compesa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Desativa o CSRF de forma segura
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/home").authenticated()  // Apenas usuários autenticados podem acessar "/home"
            .requestMatchers("/atividades").denyAll()
            .anyRequest().permitAll()  // Permite acesso a todas as outras requisições
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/home", true)
            .failureUrl("/login?error=true")
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        )
        .httpBasic(Customizer.withDefaults());  // Configura o HTTP Basic com os padrões recomendados
    return http.build();
}


    /* 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa o CSRF de forma segura
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/atividades/**").hasAnyRole("ADMINISTRADOR", "CONTROLADOR")
                .anyRequest().authenticated()
            )
            .httpBasic(); // Configura autenticação básica sem o uso de withDefaults
        return http.build();
    }
    */
    /* 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    */
    

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
