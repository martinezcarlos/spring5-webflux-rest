package mart.karle.spring5webfluxrest.controllers;

import mart.karle.spring5webfluxrest.domain.Vendor;
import mart.karle.spring5webfluxrest.repositories.VendorRepository;
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
class VendorControllerTest {

  @Mock
  private VendorRepository vendorRepository;
  @InjectMocks
  private VendorController vendorController;

  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    webTestClient = WebTestClient.bindToController(vendorController).build();
  }

  @Test
  void listVendors() {
    // Given
    BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().firstName("Name 1").lastName("LastName 1").build(),
        Vendor.builder().firstName("Name 2").lastName("LastName 2").build()));
    // When
    // Then
    webTestClient.get().uri(VendorController.BASE_PATH).exchange().expectBodyList(Vendor.class).hasSize(2);
  }

  @Test
  void getVendorById() {
    // Given
    BDDMockito.given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().firstName("Name 1").lastName("LastName 1").build()));
    // When
    // Then
    webTestClient.get().uri(VendorController.BASE_PATH + "/someid").exchange().expectBody(Vendor.class);
  }
}