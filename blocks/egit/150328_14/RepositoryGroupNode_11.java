package org.eclipse.egit.ui.internal.repository.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class RepositoryGroup {

	private UUID groupId;

	private String groupName;

	private List<File> repositoryDirectories = new ArrayList<>();

	RepositoryGroup(UUID groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	RepositoryGroup(UUID uuid, String name,
			Collection<File> repositoryDirectories) {
		this(uuid, name);
		addRepositoryDirectories(repositoryDirectories);
	}

	public UUID getGroupId() {
		return groupId;
	}

	public String getName() {
		return groupName;
	}

	public List<File> getRepositoryDirectories() {
		return new ArrayList<>(repositoryDirectories);
	}

	public boolean hasRepositories() {
		return !repositoryDirectories.isEmpty();
	}

	void addRepositoryDirectories(Collection<File> directoriesToAdd) {
		this.repositoryDirectories.addAll(directoriesToAdd);
	}

	void removeRepositoryDirectories(Collection<File> directoriesToRemove) {
		this.repositoryDirectories.removeAll(directoriesToRemove);
	}

	void setGroupName(String newName) {
		this.groupName = newName;
	}
}
