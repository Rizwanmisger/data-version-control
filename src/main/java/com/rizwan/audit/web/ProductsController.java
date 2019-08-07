
package com.rizwan.audit.web;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rizwan.audit.domain.Product;
import com.rizwan.audit.dto.VersionDTO;
import com.rizwan.audit.dto.VersionsDiffDTO;
import com.rizwan.audit.repository.ProductsRepository;
import com.rizwan.audit.service.AuditService;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

	@Autowired
	ProductsRepository productsRepository;
	@Autowired
	AuditService auditService;

	@PostMapping("")
	ResponseEntity<Product> create(@RequestBody Product product, Principal principal) {

		product.setId(UUID.randomUUID());
		product.setCreatedAt((LocalDateTime.now(ZoneOffset.UTC).withNano(0)));
		product.setUpdatedAt((LocalDateTime.now(ZoneOffset.UTC).withNano(0)));
		productsRepository.save(product);
		auditService.commit(principal.getName(), product);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	@GetMapping("")
	List<Product> list() {

		return (List<Product>) productsRepository.findAll();
	}

	@PutMapping("/{id}")
	ResponseEntity<Product> update(@PathVariable UUID id, @RequestBody Product product, Principal principal) {

		return productsRepository.findById(id).map(p -> {
			p.setName(product.getName());
			p.setDescription(product.getDescription());
			p.setUpdatedAt((LocalDateTime.now(ZoneOffset.UTC).withNano(0)));
			productsRepository.save(p);
			auditService.commit(principal.getName(), p);
			return new ResponseEntity<>(p, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("{id}/versions")
	ResponseEntity<List<VersionDTO<Product>>> getVersions(@PathVariable UUID id) {

		return productsRepository.findById(id).map(p -> {
			List<VersionDTO<Product>> list = auditService.getVersions(p, id);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping("{id}/versions/diff")
	ResponseEntity<List<VersionsDiffDTO>> getDiff(@PathVariable UUID id, @RequestParam int left, @RequestParam int right) {

		return productsRepository.findById(id).map(p -> {
			List<VersionsDiffDTO> diff = auditService.compare(Product.class, id, left, right);
			return new ResponseEntity<>(diff, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/{id}/versions")
	ResponseEntity<Product> changeVersion(@PathVariable UUID id, @RequestHeader Integer version) {

		return productsRepository.findById(id).map(cs -> {
			Product c = auditService.getVersion(Product.class, id, version);
			productsRepository.save(c);
			return new ResponseEntity<>(c, HttpStatus.OK);
		}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
