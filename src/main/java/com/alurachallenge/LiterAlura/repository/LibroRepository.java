package com.alurachallenge.LiterAlura.repository;

import com.alurachallenge.LiterAlura.model.Autor;
import com.alurachallenge.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findAutorByTituloContainsIgnoreCase(String nombreTitulo);

    @Query("SELECT l FROM Libro l LEFT JOIN FETCH l.autor")
    List<Libro> findAllWithAutores();


    List<Libro> findByIdioma(String idioma);
}
