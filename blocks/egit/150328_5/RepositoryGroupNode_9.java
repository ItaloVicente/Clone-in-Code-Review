package org.eclipse.egit.ui.internal.repository.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.egit.ui.internal.CommonUtils;

public class RepositoryGroup implements Comparable<RepositoryGroup> {

	private UUID groupId;

	private String groupName;

	private List<String> repositoryDirectories = new ArrayList<>();

	RepositoryGroup(UUID groupId, String groupName) {
		this.groupId = groupId;
		this.groupName = groupName;
	}

	RepositoryGroup(UUID uuid, String name,
			List<String> repositoryDirectories) {
		this(uuid, name);
		addRepositoryDirectories(repositoryDirectories);
	}

	public UUID getGroupId() {
		return groupId;
	}

	public String getName() {
		return groupName;
	}

	public List<String> getRepositoryDirectories() {
		return new ArrayList<>(repositoryDirectories);
	}

	public boolean hasRepositories() {
		return !repositoryDirectories.isEmpty();
	}

	void addRepositoryDirectories(List<String> directoriesToAdd) {
		this.repositoryDirectories.addAll(directoriesToAdd);
	}

	void removeRepositoryDirectories(List<String> directoriesToRemove) {
		this.repositoryDirectories.removeAll(directoriesToRemove);
	}

	void setGroupName(String newName) {
		this.groupName = newName;
	}

	@Override
	public int compareTo(RepositoryGroup o) {
		return CommonUtils.STRING_ASCENDING_COMPARATOR.compare(getName(), o.getName());
	}

}
