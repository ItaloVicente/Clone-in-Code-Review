package org.eclipse.egit.ui.internal.repository.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepositoryGroup {

	private UUID uuid;

	private String name;

	private List<String> repositories = new ArrayList<>();

	RepositoryGroup(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}

	RepositoryGroup(UUID uuid, String name, List<String> repositories) {
		this(uuid, name);
		addRepositories(repositories);
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public List<String> getRepositories() {
		return new ArrayList<>(repositories);
	}

	public boolean hasRepositories() {
		return !repositories.isEmpty();
	}

	void addRepositories(List<String> repositoriesToAdd) {
		this.repositories.addAll(repositoriesToAdd);
	}

	void removeRepositories(List<String> repositoriesToRemove) {
		this.repositories.removeAll(repositoriesToRemove);
	}

	void setName(String newName) {
		this.name = newName;
	}

}
