package mart.karle.spring5webfluxrest.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mart.karle.spring5webfluxrest.domain.Category;
import mart.karle.spring5webfluxrest.domain.Vendor;
import mart.karle.spring5webfluxrest.repositories.CategoryRepository;
import mart.karle.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@Log4j2
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

  private final CategoryRepository categoryRepository;
  private final VendorRepository vendorRepository;

  @Override
  public void run(final String... args) {
    log.debug("=== Bootstraping data ===");
    loadCategories();
    loadVendors();
    log.debug("=== Finished Bootstraping data ===");
  }

  private void loadCategories() {
    final Long count = categoryRepository.count().block();
    if (count != null && count == 0) {
      log.debug("=== Loading categories data ===");
      Stream.of(Category.builder().description("Fruits").build(),
          Category.builder().description("Nuts").build(),
          Category.builder().description("Breads").build(),
          Category.builder().description("Meats").build(),
          Category.builder().description("Eggs").build()).forEach(c -> categoryRepository.save(c).block());
      log.debug("=== Finished loading categories data ===");
    }
  }

  private void loadVendors() {
    final Long count = vendorRepository.count().block();
    if (count != null && count == 0) {
      log.debug("=== Loading vendors data ===");
      Stream.of(Vendor.builder().firstName("Joe").lastName("Buck").build(),
          Vendor.builder().firstName("Michel").lastName("Weston").build(),
          Vendor.builder().firstName("Jessie").lastName("Waters").build(),
          Vendor.builder().firstName("Bill").lastName("Elliot").build(),
          Vendor.builder().firstName("Jimmy").lastName("Stack").build()).forEach(v -> vendorRepository.save(v).block());
      log.debug("=== Finished loading vendors data ===");
    }
  }

}
