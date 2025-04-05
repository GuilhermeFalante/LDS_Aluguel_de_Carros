package com.labs.demo.labs.controllers;

import com.labs.demo.labs.models.Usuario;
import com.labs.demo.labs.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodosUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return usuarioService.buscarUsuarioPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() != null) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "ID não deve ser informado para novo usuário"
            );
        }
        
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(
            @PathVariable Long id, 
            @RequestBody Usuario usuario) {
        
        if (usuario.getId() != null && !usuario.getId().equals(id)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "ID do corpo não corresponde ao ID da URL"
            );
        }

        try {
            return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuario));
        } catch (RuntimeException e) {
            String mensagem = e.getMessage();
            HttpStatus status = mensagem.startsWith("Usuário não encontrado") 
                ? HttpStatus.NOT_FOUND 
                : HttpStatus.BAD_REQUEST;
            
            throw new ResponseStatusException(status, mensagem);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

@GetMapping("/login")
public ResponseEntity<Usuario> login(
    @RequestParam String email,
    @RequestParam String senha) {
    
    try {
        Usuario usuario = usuarioService.autenticar(email, senha);
        return ResponseEntity.ok(usuario);
    } catch (RuntimeException e) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
    }
}
}