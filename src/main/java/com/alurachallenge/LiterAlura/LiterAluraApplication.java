package com.alurachallenge.LiterAlura;

import com.alurachallenge.LiterAlura.model.DatosLibros;
import com.alurachallenge.LiterAlura.principal.Principal;
import com.alurachallenge.LiterAlura.repository.AutorRepository;
import com.alurachallenge.LiterAlura.repository.LibroRepository;
import com.alurachallenge.LiterAlura.services.ConsumoApi;
import com.alurachallenge.LiterAlura.services.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	private final Principal principal;
	@Autowired
    public LiterAluraApplication(Principal principal) {
        this.principal = principal;
    }

    public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		principal.muestraElMenu();

	}
}