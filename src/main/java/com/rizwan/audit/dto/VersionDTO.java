
package com.rizwan.audit.dto;

import java.time.LocalDateTime;

public class VersionDTO<T> {

	private Integer version;
	private boolean isCurrentVersion;
	private String author;
	private LocalDateTime createdAt;
	private T entity;

	public Integer getVersion() {

		return version;
	}

	public void setVersion(Integer version) {

		this.version = version;
	}

	public boolean isCurrentVersion() {

		return isCurrentVersion;
	}

	public void setCurrentVersion(boolean isCurrentVersion) {

		this.isCurrentVersion = isCurrentVersion;
	}

	public String getAuthor() {

		return author;
	}

	public void setAuthor(String author) {

		this.author = author;
	}

	public LocalDateTime getCreatedAt() {

		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {

		this.createdAt = createdAt;
	}

	public T getEntity() {

		return entity;
	}

	public void setEntity(T entity) {

		this.entity = entity;
	}
}
