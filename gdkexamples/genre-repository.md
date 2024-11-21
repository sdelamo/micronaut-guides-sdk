A repository interface defines the operations to access the database. Micronaut Data implements the interface at compilation time. A sample repository interface was created for you in _{{ include.parentDir }}src/main/java/com/example/repository/GenreRepository.java_:

```java
package com.example.repository;

import com.example.domain.Genre;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.repository.PageableRepository;

import jakarta.validation.constraints.NotBlank;

import static io.micronaut.data.model.query.builder.sql.Dialect.{{ include.dialect }};

@JdbcRepository(dialect = {{ include.dialect }}) // <1>
public interface GenreRepository extends PageableRepository<Genre, Long> { // <2>

    Genre save(@NonNull @NotBlank String name);

    long update(@Id long id, @NonNull @NotBlank String name);
}
```

**1** [`@JdbcRepository`](https://micronaut-projects.github.io/micronaut-data/3.0.1/api/io/micronaut/data/jdbc/annotation/JdbcRepository.html){:target="_blank"} with a specific dialect.

**2** `Genre`, the entity to treat as the root entity for the purposes of querying, is established either from the method signature or from the generic type parameter specified to the `GenericRepository` interface.

The repository extends from `PageableRepository`. It inherits the hierarchy `PageableRepository` → `CrudRepository` → `GenericRepository`.

| Repository | Description |
| --- | --- |
| `PageableRepository` | A repository that supports pagination. <br>It provides `findAll(Pageable)` and `findAll(Sort)`. |
| `CrudRepository` | A repository interface for performing CRUD (Create, Read, Update, Delete). <br>It provides methods such as `findAll()`, `save(Genre)`, `deleteById(Long)`, and `findById(Long)`. |
| `GenericRepository` | A root interface that features no methods but defines the entity type and ID type as generic arguments. |