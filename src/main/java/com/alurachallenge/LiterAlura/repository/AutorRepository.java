package com.alurachallenge.LiterAlura.repository;

import com.alurachallenge.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.anioDeNacimiento <= :anio AND (a.anioDeNacimiento IS NULL OR a.anioDeMuerte > :anio)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    Autor findByNombre(String nombreAutor);

    List<Autor> findAutorByNombreContainsIgnoreCase(String nombreAutor);

}
