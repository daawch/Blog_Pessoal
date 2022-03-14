package org.Generation.blogPessoal.controller;

import java.util.Optional;

import org.Generation.blogPessoal.model.User;
import org.Generation.blogPessoal.model.UserLogin;
import org.Generation.blogPessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/logar")
    public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user){
        return usuarioService.logar(user).map(resp -> ResponseEntity.ok(resp))
        .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build ());
    }
    @PostMapping("/cadastrar")
    public ResponseEntity<User> Post(@RequestBody User usuario){
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(usuarioService.cadastrarUsuario(usuario));
    }
    
}