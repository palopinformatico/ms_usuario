package com.microservice.usuarios.resource;

import org.springframework.http.ResponseEntity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.usuarios.model.domain.Phone;
import com.microservice.usuarios.model.domain.User;
import com.microservice.usuarios.model.dto.UserDto;
import com.microservice.usuarios.service.UsuarioService;
import com.microservice.usuarios.utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@SpringBootApplication
@RequestMapping(value = "/api/usuarios/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    /*@GetMapping(value = "/traeUsuario", produces = "application/json")
    public ResponseEntity<String> traeUsuario(@RequestBody User usuario) throws Exception {
    	
    	String mensajeSalida = UsuarioService.findUser(usuario);
    	
    	return ResponseEntity.ok(mensajeSalida); 
    }*/

    @PostMapping(value = "/creaUsuario", produces = "application/json")
    public ResponseEntity<String> creaUsuario(@RequestBody UserDto userDto) throws Exception {
    	
    	String mensajeSalida = UsuarioService.createUser(userDto);
    	
    	return ResponseEntity.ok(mensajeSalida);    	
        
    }
	
	public static void main(String[] args) {
		SpringApplication.run(UsuarioResource.class, args);
	}

}
