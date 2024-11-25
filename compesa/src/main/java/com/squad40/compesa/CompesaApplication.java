package com.squad40.compesa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import io.github.cdimascio.dotenv.Dotenv;



@SpringBootApplication
public class CompesaApplication {

 
    public static void main(String[] args) {

        //Dotenv dotenv = Dotenv.load(); 
       // System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
       // System.setProperty("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));

        SpringApplication.run(CompesaApplication.class, args);

    }
}