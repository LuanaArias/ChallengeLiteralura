# ChallengeLiteralura - Gestor de Libros

LiterAlura es una aplicación Java desarrollada con **Spring Boot** y **JPA/Hibernate** que permite gestionar libros y sus autores. 

## 🗃️ Estructura de clases principales

### `Libro.java`
Entidad que representa un libro:
- `id`: identificador único.
- `titulo`: título del libro.
- `cantDescargas`: cantidad de descargas.
- `idioma`: idioma del libro.
- `autor`: relación `@ManyToOne` con la entidad `Autor`.


### `Autor.java`
Entidad que representa un autor:
- `id`: identificador único.
- `nombre`: nombre del autor.
- `anioDeNacimiento`: año de nacimiento del autor.
- `anioDeMuerte`: año de fallecimiento del autor.
- `libros`: relación `@OneToMany` con la entidad `Libro`.
