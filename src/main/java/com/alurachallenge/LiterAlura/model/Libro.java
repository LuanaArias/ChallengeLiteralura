package com.alurachallenge.LiterAlura.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Integer cantDescargas;
    private String idioma;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    public String getTitulo() {
        return titulo;
    }

    public Integer getCantDescargas() {
        return cantDescargas;
    }

    public Autor getAutor() {
        return autor;
    }



    public Libro() {}

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.cantDescargas = datosLibros.cantDescargas();
        this.idioma = datosLibros.idioma().isEmpty() ? "desconocido" : datosLibros.idioma().get(0);

   }

    public Libro(String titulo, Integer cantDescargas, String idioma, Autor autor) {
        this.titulo = titulo;
        this.cantDescargas = cantDescargas;
        this.idioma = idioma;
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    @Override
    public String toString() {
        return "Titulo: '" + titulo + '\'' +
                ", cantidad de descargas: " + cantDescargas +
                ", autores: " + autor ;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

}
