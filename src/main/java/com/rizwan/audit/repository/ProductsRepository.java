
package com.rizwan.audit.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.rizwan.audit.domain.Product;

public interface ProductsRepository extends CrudRepository<Product, UUID> {
}
