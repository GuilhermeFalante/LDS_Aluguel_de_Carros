package com.labs.demo.labs.services;

import com.labs.demo.labs.Enums.TIPO_DONO;
import com.labs.demo.labs.models.Automovel;
import com.labs.demo.labs.repositories.AutomovelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutomovelService {

    private final AutomovelRepository automovelRepository;

    public AutomovelService(AutomovelRepository automovelRepository) {
        this.automovelRepository = automovelRepository;
    }

    public Automovel cadastrarAutomovel(Automovel automovel) {
        if (automovelRepository.existsByPlaca(automovel.getPlaca())) {
            throw new RuntimeException("Placa já está em uso");
        }
        return automovelRepository.save(automovel);
    }

    public Automovel alterarDisponibilidade(String matricula, boolean disponivel) {
        return automovelRepository.findById(matricula)
                .map(automovel -> {
                    automovel.setDisponivel(disponivel);
                    return automovelRepository.save(automovel);
                })
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com a matrícula: " + matricula));
    }

    public List<Automovel> listarTodosAutomoveis() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> buscarAutomovelPorMatricula(String matricula) {
        return automovelRepository.findById(matricula);
    }

    public List<Automovel> buscarAutomoveisPorTipoDono(TIPO_DONO tipoDono) {
        return automovelRepository.findByTipoDono(tipoDono);
    }

    public Optional<Automovel> buscarAutomovelPorPlaca(String placa) {
        return Optional.ofNullable(automovelRepository.findByPlaca(placa));
    }

    public Automovel atualizarAutomovel(String matricula, Automovel automovelAtualizado) {
        return automovelRepository.findById(matricula)
                .map(automovel -> {
                    if (!automovel.getPlaca().equals(automovelAtualizado.getPlaca()) &&
                        automovelRepository.existsByPlaca(automovelAtualizado.getPlaca())) {
                        throw new RuntimeException("Placa já está em uso por outro automóvel");
                    }

                    automovel.setAno(automovelAtualizado.getAno());
                    automovel.setMarca(automovelAtualizado.getMarca());
                    automovel.setModelo(automovelAtualizado.getModelo());
                    automovel.setPlaca(automovelAtualizado.getPlaca());
                    automovel.setTipoDono(automovelAtualizado.getTipoDono());
                    automovel.setDisponivel(automovelAtualizado.getDisponivel());
                    
                    return automovelRepository.save(automovel);
                })
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com a matrícula: " + matricula));
    }

    public void deletarAutomovel(String matricula) {
        if (!automovelRepository.existsById(matricula)) {
            throw new RuntimeException("Automóvel não encontrado com a matrícula: " + matricula);
        }
        automovelRepository.deleteById(matricula);
    }

    public List<Automovel> buscarAutomoveisPorMarcaEModelo(String marca, String modelo) {
        return automovelRepository.findByMarcaAndModelo(marca, modelo);
    }

    public List<Automovel> buscarAutomoveisPorAnoMinimo(int ano) {
        return automovelRepository.findByAnoGreaterThanEqual(ano);
    }
}