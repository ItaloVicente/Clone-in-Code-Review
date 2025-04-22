package org.eclipse.egit.ui.internal.repository.tree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.util.StringUtils;
import org.osgi.service.prefs.BackingStoreException;

public class RepositoryGroups {

	private static RepositoryGroups INSTANCE = new RepositoryGroups();

	private Map<UUID, RepositoryGroup> groupMap = new HashMap<>();

	private IEclipsePreferences preferences = Activator.getDefault()
			.getRepositoryUtil().getPreferences();

	private static final String PREFS_GROUPS = "GitRepositoriesView.RepositoryGroups.uuids"; //$NON-NLS-1$

	private static final String PREFS_GROUP_NAME_PREFIX = "GitRepositoriesView.RepositoryGroups."; //$NON-NLS-1$

	private static final String PREFS_GROUP_PREFIX = "GitRepositoriesView.RepositoryGroups.group."; //$NON-NLS-1$

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String SEPARATOR = "\n";//$NON-NLS-1$

	public static RepositoryGroups getInstance() {
		return INSTANCE;
	}
	private RepositoryGroups() {
		List<String> groups = split(
				preferences.get(PREFS_GROUPS, EMPTY_STRING));
		for (String groupIdString : groups) {
			UUID groupId = UUID.fromString(groupIdString);
			String name = preferences
					.get(PREFS_GROUP_NAME_PREFIX + groupIdString, EMPTY_STRING);
			List<File> repos = split(preferences
					.get(PREFS_GROUP_PREFIX + groupIdString, EMPTY_STRING))
							.stream().map(File::new)
							.collect(Collectors.toList());
			RepositoryGroup group = new RepositoryGroup(groupId, name, repos);
			groupMap.put(groupId, group);
		}
	}

	private List<String> split(String input) {
		List<String> result = new ArrayList<>();
		String[] split = input.split(SEPARATOR);
		for (String string : split) {
			if (string != null) {
				String trimmed = string.trim();
				if (trimmed.length() > 0) {
					result.add(trimmed);
				}
			}
		}
		return result;
	}

	public UUID addGroup(String groupName) {
		if (!groupExists(groupName)) {
			String trimmedName = groupName.trim();
			UUID groupId = UUID.randomUUID();
			RepositoryGroup group = new RepositoryGroup(groupId, trimmedName);
			groupMap.put(groupId, group);
			savePreferences();
			return groupId;
		} else {
			throw new IllegalStateException(
					UIText.RepositoriesView_RepoGroup_GroupExists);
		}
	}

	public void renameGroup(RepositoryGroup group, String newName) {
		checkGroupName(newName);
		RepositoryGroup myGroup = groupMap.get(group.getGroupId());
		if (myGroup != null) {
			myGroup.setGroupName(newName);
			savePreferences();
		}
	}

	public boolean groupExists(String groupName)
			throws IllegalArgumentException {
		checkGroupName(groupName);
		for (RepositoryGroup group : groupMap.values()) {
			if (group.getName().equals(groupName)) {
				return true;
			}
		}
		return false;
	}

	private void checkGroupName(String groupName) {
		if (StringUtils.isEmptyOrNull(groupName)
				|| !groupName.equals(groupName.trim())) {
			throw new IllegalArgumentException(
					UIText.RepositoriesView_RepoGroup_InvalidNameException);
		}
	}

	public void addRepositoriesToGroup(UUID groupId,
			Collection<File> repoDirs) {
		if (!groupMap.containsKey(groupId)) {
			throw new IllegalArgumentException();
		}
		Collection<RepositoryGroup> currentGroups = groupMap.values();
		for (RepositoryGroup groups : currentGroups) {
			groups.removeRepositoryDirectories(repoDirs);
		}

		groupMap.get(groupId).addRepositoryDirectories(repoDirs);
		savePreferences();

	}

	public void delete(Collection<RepositoryGroup> groupsToDelete) {
		for (RepositoryGroup group : groupsToDelete) {
			preferences
					.remove(PREFS_GROUP_PREFIX + group.getGroupId());
			groupMap.remove(group.getGroupId());
		}
		savePreferences();
	}

	private void savePreferences() {
		try {
			List<String> groupIds = new ArrayList<>();
			for (RepositoryGroup group : groupMap.values()) {
				String groupId = group.getGroupId().toString();
				groupIds.add(groupId);
				String name = group.getName();
				preferences.put(PREFS_GROUP_NAME_PREFIX + groupId, name);
				String repos = group.getRepositoryDirectories().stream()
						.map(File::toString)
						.collect(Collectors.joining(SEPARATOR));
				preferences.put(PREFS_GROUP_PREFIX + groupId, repos);
			}
			preferences.put(PREFS_GROUPS,
					StringUtils.join(groupIds, SEPARATOR));
			preferences.flush();
		} catch (BackingStoreException e) {
			Activator.logError(
					UIText.RepositoriesView_RepoGroup_ErrorSavePreferences, e);
		}
	}

	public List<RepositoryGroup> getGroups() {
		return new ArrayList<>(groupMap.values());
	}

	public boolean belongsToGroup(File repositoryDirectory) {
		for (RepositoryGroup group : groupMap.values()) {
			if (group.getRepositoryDirectories()
					.contains(repositoryDirectory)) {
				return true;
			}
		}
		return false;
	}

	public void removeFromGroups(List<File> repositoryDirecptories) {
		for (RepositoryGroup group : groupMap.values()) {
			group.removeRepositoryDirectories(repositoryDirecptories);
		}
		savePreferences();
	}
}
