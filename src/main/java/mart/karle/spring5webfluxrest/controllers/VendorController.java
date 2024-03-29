package mart.karle.spring5webfluxrest.controllers;

import lombok.RequiredArgsConstructor;
import mart.karle.spring5webfluxrest.domain.Vendor;
import mart.karle.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  Flux<Vendor> createVendors(@RequestBody final Publisher<Vendor> publisher) {
    return vendorRepository.saveAll(publisher);
  }

  @PutMapping("/{id}")
  Mono<Vendor> updateVendor(@PathVariable final String id, @RequestBody final Vendor vendor) {
    vendor.setId(id);
    return vendorRepository.save(vendor);
  }

}
