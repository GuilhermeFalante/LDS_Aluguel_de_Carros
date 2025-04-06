package com.labs.demo.labs.repositories;

import com.labs.demo.labs.Enums.TIPO_DONO;
import com.labs.demo.labs.models.Automovel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AutomovelRepository extends JpaRepository<Automovel, String> {

    Automovel findByPlaca(String placa);
    
    List<Automovel> findByTipoDono(TIPO_DONO tipoDono);
    
    List<Automovel> findByMarcaAndModelo(String marca, String modelo);
    
    List<Automovel> findByAnoGreaterThanEqual(int ano);
    
    @Query("SELECT a FROM Automovel a WHERE a.marca = :marca AND a.ano BETWEEN :anoInicio AND :anoFim")
    List<Automovel> findPorMarcaEIntervaloAno(
        @Param("marca") String marca,
        @Param("anoInicio") int anoInicio,
        @Param("anoFim") int anoFim);
    
    long countByTipoDono(TIPO_DONO tipoDono);
    
    boolean existsByPlaca(String placa);
}