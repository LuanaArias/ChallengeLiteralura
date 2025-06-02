package com.alurachallenge.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros = new ArrayList<>();
    public Autor(){}

    public Autor(DatosAutores datosAutores){
        this.nombre = datosAutores.nombre();
        this.anioDeNacimiento = datosAutores.anioDeNacimiento();
        this.anioDeMuerte = datosAutores.anioDeMuerte();
    }


    public void agregarLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNaciomiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNaciomiento(Integer anioDeNaciomiento) {
        this.anioDeNacimiento = anioDeNaciomiento;
    }

    public Integer getAnioDeMuerte() {
        return anioDeMuerte;
    }

    public void setAnioDeMuerte(Integer anioDeMuerte) {
        this.anioDeMuerte = anioDeMuerte;
    }



    public Autor(String nombre, Integer anioDeNacimiento, Integer anioDeMuerte){
        this.nombre = nombre;
        this.anioDeNacimiento = anioDeNacimiento;
        this.anioDeMuerte = anioDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return "Nombre: '" + nombre + '\'' +
                ", año de naciomiento: " + anioDeNacimiento +
                ", año de muerte: " + anioDeMuerte;
    }
}
