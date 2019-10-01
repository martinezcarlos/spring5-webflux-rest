package mart.karle.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import mart.karle.spring5webfluxrest.domain.Category;
import mart.karle.spring5webfluxrest.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
