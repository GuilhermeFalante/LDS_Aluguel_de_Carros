package com.labs.demo.labs.controllers;

import com.labs.demo.labs.Enums.TIPO_DONO;
import com.labs.demo.labs.models.Automovel;
import com.labs.demo.labs.services.AutomovelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/automoveis")
public class AutomovelController {

    private final AutomovelService automovelService;

    public AutomovelController(AutomovelService automovelService) {
        this.automovelService = automovelService;
    }

    @GetMapping
    public ResponseEntity<List<Automovel>> listarTodos() {
        return ResponseEntity.ok(automovelService.listarTodosAutomoveis());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Automovel> buscarPorMatricula(@PathVariable String matricula) {
        return automovelService.buscarAutomovelPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/placa")
    public ResponseEntity<Automovel> buscarPorPlaca(@RequestParam String placa) {
        return automovelService.buscarAutomovelPorPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/tipo-dono")
    public ResponseEntity<List<Automovel>> buscarPorTipoDono(@RequestParam TIPO_DONO tipoDono) {
        return ResponseEntity.ok(automovelService.buscarAutomoveisPorTipoDono(tipoDono));
    }

    @PostMapping
    public ResponseEntity<Automovel> criarAutomovel(@RequestBody Automovel automovel) {
        try {
            Automovel novoAutomovel = automovelService.cadastrarAutomovel(automovel);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAutomovel);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Automovel> atualizarAutomovel(
            @PathVariable String matricula, 
            @RequestBody Automovel automovel) {
        
        if (automovel.getMatricula() != null && !automovel.getMatricula().equals(matricula)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Matrícula do corpo não corresponde à matrícula da URL"
            );
        }

        try {
            return ResponseEntity.ok(automovelService.atualizarAutomovel(matricula, automovel));
        } catch (RuntimeException e) {
            String mensagem = e.getMessage();
            HttpStatus status = mensagem.startsWith("Automóvel não encontrado") 
                ? HttpStatus.NOT_FOUND 
                : HttpStatus.BAD_REQUEST;
            
            throw new ResponseStatusException(status, mensagem);
        }
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> deletarAutomovel(@PathVariable String matricula) {
        try {
            automovelService.deletarAutomovel(matricula);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}