package mart.karle.spring5webfluxrest.controllers;

import mart.karle.spring5webfluxrest.domain.Vendor;
import mart.karle.spring5webfluxrest.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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
    given(vendorRepository.findAll()).willReturn(Flux.just(Vendor.builder().firstName("Name 1").lastName("LastName 1").build(),
        Vendor.builder().firstName("Name 2").lastName("LastName 2").build()));
    // When
    // Then
    webTestClient.get().uri(VendorController.BASE_PATH).exchange().expectBodyList(Vendor.class).hasSize(2);
  }

  @Test
  void getVendorById() {
    // Given
    given(vendorRepository.findById(anyString())).willReturn(Mono.just(Vendor.builder().firstName("Name 1").lastName("LastName 1").build()));
    // When
    // Then
    webTestClient.get().uri(VendorController.BASE_PATH + "/someid").exchange().expectBody(Vendor.class);
  }

  @Test
  void createVendor() {
    // Given
    given(vendorRepository.saveAll(any(Publisher.class))).willReturn(Flux.just(Vendor.builder().build()));
    final Mono<Vendor> venToSaveMono = Mono.just(Vendor.builder().firstName("Name 1").build());
    // When
    // Then
    webTestClient.post().uri(VendorController.BASE_PATH).body(venToSaveMono, Vendor.class).exchange().expectStatus().isCreated();
  }

  @Test
  void updateVendor() {
    // Given
    given(vendorRepository.save(any(Vendor.class))).willReturn(Mono.just(Vendor.builder().build()));
    final Mono<Vendor> venToUpdateMono = Mono.just(Vendor.builder().id("someid").firstName("Name 1").build());
    // When
    // Then
    webTestClient.put().uri(VendorController.BASE_PATH + "/newid").body(venToUpdateMono, Vendor.class).exchange().expectStatus().isOk();
  }
}