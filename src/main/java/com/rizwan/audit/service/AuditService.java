
package com.rizwan.audit.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rizwan.audit.dto.VersionDTO;
import com.rizwan.audit.dto.VersionsDiffDTO;

@Service
public class AuditService {

	@Autowired
	private final Javers javers;

	public AuditService(Javers javers) {

		this.javers = javers;
	}

	public <T> void commit(String author, T currentVersion) {

		javers.commit(author, currentVersion);
	}

	public <T> List<VersionDTO<T>> getVersions(T currentVersion, Object id) {

		List<Shadow<T>> ds = getShadows(currentVersion.getClass(), id);
		AtomicInteger index = new AtomicInteger();
		return ds.stream().map(d -> {
			VersionDTO<T> version = new VersionDTO<>();
			version.setEntity(d.get());
			version.setVersion(index.getAndIncrement());
			version.setAuthor(d.getCommitMetadata().getAuthor());
			version.setCreatedAt(d.getCommitMetadata().getCommitDate());
			if ( !javers.compare(currentVersion, d.get()).hasChanges()) {
				version.setCurrentVersion(true);
			}
			return version;
		}).collect(Collectors.toList());
	}

	public <T> List<VersionsDiffDTO> compare(Class<?> entity, Object id, int left, int right) {

		List<Shadow<T>> shadows = getShadows(entity, id);
		T v1 = shadows.get(left).get();
		T v2 = shadows.get(right).get();
		List<Change> changes = javers.compare(v1, v2).getChanges();
		return changes.parallelStream().map(change -> {
			VersionsDiffDTO diff = new VersionsDiffDTO();
			diff.setPropertyName(((ValueChange) change).getPropertyName());
			diff.setPropertyNameWithPath(((ValueChange) change).getPropertyNameWithPath());
			diff.setLeft(((ValueChange) change).getLeft());
			diff.setRight(((ValueChange) change).getRight());
			return diff;
		}).collect(Collectors.toList());
	}

	public <T> T getVersion(Class<T> entity, Object id, int version) {

		List<Shadow<T>> shadows = getShadows(entity, id);
		return shadows.get(version).get();
	}

	private <T> List<Shadow<T>> getShadows(Class<?> entity, Object id) {

		QueryBuilder jqlQuery = QueryBuilder.byInstanceId(id, entity);
		List<Shadow<T>> shadows = javers.findShadows(jqlQuery.build());
		Collections.reverse(shadows);
		return shadows;
	}
}
