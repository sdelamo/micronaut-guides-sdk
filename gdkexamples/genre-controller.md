Hibernate Validator is a reference implementation of the Validation API. Micronaut [has built-in support for validation of beans](https://docs.micronaut.io/latest/guide/#beanValidation){:target="_blank"} that use `jakarta.validation` annotations. The necessary dependencies are included by default when creating a project.

The GDK Launcher created the main controller that exposes a resource with the common CRUD operations for you in _{{ include.parentDir }}src/main/java/com/example/controller/GenreController.java_:

```java
package com.example.controller;

import com.example.domain.Genre;
import com.example.service.GenreService;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static io.micronaut.http.HttpHeaders.LOCATION;
import static io.micronaut.http.HttpStatus.NO_CONTENT;

@ExecuteOn(TaskExecutors.IO) // <1>
@Controller("/genres") // <2>
class GenreController {

    private final GenreService genreService;

    GenreController(GenreService genreService) { // <3>
        this.genreService = genreService;
    }

    @Get("/{id}") // <4>
    public Optional<Genre> show(Long id) {
        return genreService.findById(id);
    }

    @Put("/{id}/{name}") // <5>
    public HttpResponse<?> update(long id, String name) {
        genreService.update(id, name);
        return HttpResponse
                .noContent()
                .header(LOCATION, URI.create("/genres/" + id).getPath());
    }

    @Get("/list") // <6>
    public List<Genre> list(@Valid Pageable pageable) {
        return genreService.list(pageable);
    }

    @Post // <7>
    public HttpResponse<Genre> save(@Body("name") @NotBlank String name) {
        Genre genre = genreService.save(name);

        return HttpResponse
                .created(genre)
                .headers(headers -> headers.location(URI.create("/genres/" + genre.getId())));
    }

    @Delete("/{id}") // <8>
    @Status(NO_CONTENT)
    public void delete(Long id) {
        genreService.delete(id);
    }
}
```

**1** It is critical that any blocking I/O operations (such as fetching the data from the database) are offloaded to a separate thread pool that does not block the event loop.

**2** The class is defined as a controller with the [`@Controller`](https://docs.micronaut.io/latest/api/io/micronaut/http/annotation/Controller.html){:target="_blank"} annotation mapped to the path `/genres`.

**3** Uses constructor injection to inject a bean of type `GenreRepository`.

**4** Maps a `GET` request to `/genres/{id}`, which attempts to show a genre. This illustrates the use of a URL path variable (`id`).

**5** Maps a `PUT` request to `/genres/{id}/{name}`, which attempts to update a genre. This illustrates the use of URL path variables (`id` and `name`).

**6** Maps a `GET` request to `/genres/list`, which returns a list of genres. This mapping illustrates URL parameters being mapped to a single POJO.

**7** Maps a `POST` request to `/genres`, which attempts to create a new genre.

**8** Maps a `DELETE` request to `/genres/{id}`, which attempts to remove a genre. This illustrates the use of a URL path variable (`id`).
