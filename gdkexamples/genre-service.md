A service contains business logic and facilitates communication between the controller and the repository. The domain is used to communicate between the controller and service layers.

The GDK Launcher created a sample service class, _{{ include.parentDir }}src/main/java/com/example/service/GenreService.java_, for you:

```java
package com.example.service;

import com.example.domain.Genre;
import com.example.repository.GenreRepository;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class GenreService {

    private final GenreRepository genreRepository;

    GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Transactional
    public long update(long id, String name) {
        return genreRepository.update(id, name);
    }

    public List<Genre> list(Pageable pageable) {
        return genreRepository.findAll(pageable).getContent();
    }

    @Transactional
    public Genre save(String name) {
        return genreRepository.save(name);
    }

    @Transactional
    public void delete(long id) {
        genreRepository.deleteById(id);
    }
}
```