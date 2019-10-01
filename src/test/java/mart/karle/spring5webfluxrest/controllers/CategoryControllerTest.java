package mart.karle.spring5webfluxrest.controllers;

import mart.karle.spring5webfluxrest.domain.Category;
import mart.karle.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

  private WebTestClient webTestClient;
  @Mock
  private CategoryRepository categoryRepository;
  @InjectMocks
  private CategoryController categoryController;

  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(categoryController).build();
  }

  @Test
  void listCategories() {
    // Given
    BDDMockito.given(categoryRepository.findAll()).willReturn(Flux.just(Category.builder().description("Cat 1").build(),
        Category.builder().description("Cat 2").build()));
    // When
    // Then
    webTestClient.get().uri(CategoryController.BASE_PATH).exchange().expectBodyList(Category.class).hasSize(2);
  }

  @Test
  void getById() {
    // Given
    BDDMockito.given(categoryRepository.findById(anyString())).willReturn(Mono.just(Category.builder().id("someid").description("Cat 1").build()));
    // When
    // Then
    webTestClient.get().uri(CategoryController.BASE_PATH + "/someid").exchange().expectBody(Category.class);
  }
}