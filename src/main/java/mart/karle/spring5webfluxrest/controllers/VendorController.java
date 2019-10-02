package mart.karle.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import mart.karle.spring5webfluxrest.domain.Vendor;
import mart.karle.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(VendorController.BASE_PATH)
public class VendorController {

  static final String BASE_PATH = "/vendors";

  private final VendorRepository vendorRepository;

  @GetMapping
  Flux<Vendor> listVendors() {
    return vendorRepository.findAll();
  }

  @GetMapping("/{id}")
  Mono<Vendor> getVendorById(@PathVariable final String id) {
    return vendorRepository.findById(id);
  }

}
