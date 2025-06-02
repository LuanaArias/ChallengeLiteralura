package com.alurachallenge.LiterAlura.principal;

import com.alurachallenge.LiterAlura.model.*;
import com.alurachallenge.LiterAlura.repository.AutorRepository;
import com.alurachallenge.LiterAlura.repository.LibroRepository;
import com.alurachallenge.LiterAlura.services.ConsumoApi;
import com.alurachallenge.LiterAlura.services.ConvierteDatos;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
@Component
public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private Scanner scanner = new Scanner(System.in);
    private List<Libro> libros;


    @Autowired
    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println();
        System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

        while (true) {
            var opcion = 0;
            var menu = (""" 
                    1- Buscar libro guardados ingresando su titulo:
                    2- Buscar autor guardados ingresando su nombre:
                    3- Guardar un libro y sus autores:
                    4- Mostrar libros buscados:
                    5- Listar autores guardados:
                    6- Listar autores vivos en un año:
                    7- Salir
                    """);
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    buscarAutor();
                    break;
                case 3:
                    guardarDatosLibros();
                    break;
                case 4:
                    mostrarLibrosBuscados();
                    break;
                case 5:
                    listarAutores();
                    break;
                case 6:
                    listarAutoresVivosEnAnio();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Nombre del libro: ");
        var nombreTitulo = scanner.nextLine();
        List<Libro> librosEncontrados = libroRepository.findAutorByTituloContainsIgnoreCase(nombreTitulo);

        if (!librosEncontrados.isEmpty()) {
            System.out.println("Libros encontrados:");
            for (Libro libro : librosEncontrados) {
                System.out.println(libro);
            }
        } else {
            System.out.println("Libros no encontrado. Intente de nuevo con otro titulo de libro.");
        }
    }

    private void buscarAutor() {
        System.out.println("Nombre del autor: ");
        var nombreAutor = scanner.nextLine();
        List<Autor> autoresEncontrados = autorRepository.findAutorByNombreContainsIgnoreCase(nombreAutor);

        if (!autoresEncontrados.isEmpty()) {
            System.out.println("Autores encontrados:");
            for (Autor autor : autoresEncontrados) {
                System.out.println(autor);
            }
        } else {
            System.out.println("Autor no encontrado. Intente de nuevo con otro nombre de autor.");
        }
    }

    public DatosLibros guardarDatosLibros() {
        System.out.print("Nombre del libro: ");
        String nombreTitulo = scanner.nextLine().trim();

        String json = consumoApi.obtenerDatos(URL_BASE);
        Datos datos = conversor.obtenerDatos(json, Datos.class);

        for (DatosLibros libro : datos.resultados()) {
            if (libro.titulo().equalsIgnoreCase(nombreTitulo)) {
                Autor autorEntidad = null;

                if (libro.autores() != null && !libro.autores().isEmpty()) {
                    DatosAutores datosAutor = libro.autores().get(0);
                    String nombreAutor = datosAutor.nombre().toLowerCase();

                    autorEntidad = autorRepository.findByNombre(nombreAutor);

                    if (autorEntidad == null) {
                        autorEntidad = new Autor(
                                datosAutor.nombre(),
                                datosAutor.anioDeNacimiento(),
                                datosAutor.anioDeMuerte()
                        );
                        autorRepository.save(autorEntidad);
                    }
                }

                Libro libroEntidad = new Libro(libro);

                libroEntidad.setAutor(autorEntidad);
                if (autorEntidad != null) {
                    autorEntidad.getLibros().add(libroEntidad);
                }

                libroRepository.save(libroEntidad);

                return libro;
            }
        }

        System.out.println("Libro no encontrado.");
        return null;
    }

    @Transactional
    public void mostrarLibrosBuscados(){
        libros = libroRepository.findAllWithAutores();
        libros.forEach(System.out::println);
    }
    public void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma (ej: 'en', 'es'):");
        String idioma = scanner.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            libros.forEach(libro -> System.out.println("📘 " + libro.getTitulo() +
                    "Sus autores son: " + libro.getAutor() + "Tiene "
                    + libro.getCantDescargas() + " descargas." ));
        }
    }
    public void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese un año para buscar autores vivos:");
        int anio = scanner.nextInt();
        scanner.nextLine();

        List<Autor> vivos = autorRepository.findAutoresVivosEnAnio(anio);
        if (vivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            vivos.forEach(System.out::println);
        }
    }
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
        } else {
            System.out.println("Autores guardados:");
            autores.forEach(System.out::println);
        }
    }
    public Principal(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

}
