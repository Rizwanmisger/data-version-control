
package com.rizwan.audit.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rizwan.audit.domain.Product;
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
	ResponseEntity<Product> create(@RequestBody Product product) {

		product.setId(UUID.randomUUID());
		productsRepository.save(product);
		auditService.commit("user", product);
		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	@GetMapping("")
	List<Product> list() {

		return (List<Product>) productsRepository.findAll();
	}
}
