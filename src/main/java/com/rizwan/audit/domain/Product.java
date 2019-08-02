
package com.rizwan.audit.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {

	@Id
	private UUID id;
	@NotNull
	private String name;
	private String description;

	public UUID getId() {

		return id;
	}

	public void setId(UUID id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}
}
