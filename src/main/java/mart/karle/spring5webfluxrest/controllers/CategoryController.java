package mart.karle.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import mart.karle.spring5webfluxrest.domain.Category;
import mart.karle.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(CategoryController.BASE_PATH)
public class CategoryController {

  static final String BASE_PATH = "/categories";

  private final CategoryRepository categoryRepository;

  @GetMapping
  Flux<Category> listCategories() {
    return categoryRepository.findAll();
  }

  @GetMapping("/{id}")
  Mono<Category> getById(@PathVariable final String id) {
    return categoryRepository.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  Mono<Void> createCategory(@RequestBody final Publisher<Category> categoryStream) {
    return categoryRepository.saveAll(categoryStream).then();
  }

  @PutMapping("/{id}")
  Mono<Category> updateCategory(@PathVariable final String id, @RequestBody final Category category) {
    category.setId(id);
    return categoryRepository.save(category);
  }

  @PatchMapping("/{id}")
  Mono<Category> patchCategory(@PathVariable final String id, @RequestBody final Category category) {
    final Category categoryToUpdate = categoryRepository.findById(id).block();
    if (category.getDescription() != null && !category.getDescription().equals(categoryToUpdate.getDescription())) {
      categoryToUpdate.setDescription(category.getDescription());
      return categoryRepository.save(category);
    }
    return Mono.just(categoryToUpdate);
  }
}
