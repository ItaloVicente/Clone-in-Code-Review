package org.eclipse.egit.ui.internal.repository.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jgit.util.StringUtils;
import org.osgi.service.prefs.BackingStoreException;

public class RepositoryGroups {


	private Map<UUID, RepositoryGroup> groupMap = new HashMap<>();

	private IEclipsePreferences preferences = Activator.getDefault()
			.getRepositoryUtil().getPreferences();

	private static final String PREFS_GROUPS = "GitRepositoriesView.RepositoryGroups.uuids"; //$NON-NLS-1$

	private static final String PREFS_GROUP_NAME_PREFIX = "GitRepositoriesView.RepositoryGroups."; //$NON-NLS-1$

	private static final String PREFS_GROUP_PREFIX = "GitRepositoriesView.RepositoryGroups.group."; //$NON-NLS-1$

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String SEPARATOR = "\n";//$NON-NLS-1$
	public RepositoryGroups() {
		List<String> groups = split(
				preferences.get(PREFS_GROUPS, EMPTY_STRING));
		for (String groupUUIDString : groups) {
			UUID groupUUIDuuid = UUID.fromString(groupUUIDString);
			String name = preferences
					.get(PREFS_GROUP_NAME_PREFIX + groupUUIDString,
							EMPTY_STRING);
			List<String> repos = split(
					preferences.get(PREFS_GROUP_PREFIX + groupUUIDString,
							EMPTY_STRING));
			RepositoryGroup group = new RepositoryGroup(groupUUIDuuid, name,
					repos);
			groupMap.put(groupUUIDuuid, group);
		}
	}

	private List<String> split(String input) {
		List<String> result=new ArrayList<>();
		String[] split = input.split(SEPARATOR);
		for (String string : split) {
			if(string != null && string.trim().length()>0) {
				result.add(string.trim());
			}
		}
		return result;
	}

	public Optional<UUID> addGroup(String groupName) {
		if (!groupExists(groupName)) {
			String trimmedName = groupName.trim();
			UUID groupUUIDuuid = UUID.randomUUID();
			RepositoryGroup group = new RepositoryGroup(groupUUIDuuid,
					trimmedName);
			groupMap.put(groupUUIDuuid, group);
			savePreferences();
			return Optional.of(groupUUIDuuid);
		}
		return Optional.empty();
	}

	public void renameGroup(RepositoryGroup group, String newName) {
		RepositoryGroup myGroup = groupMap.get(group.getUuid());
		if (myGroup != null) {
			myGroup.setName(newName);
			savePreferences();
		}
	}

	public boolean groupExists(String groupName) {
		if (groupName != null && !StringUtils.isEmptyOrNull(groupName)) {
			String trimmedName = groupName.trim();
			for (RepositoryGroup group : groupMap.values()) {
				if (group.getName().equals(trimmedName)) {
					return true;
				}
			}
		}
		return false;
	}

	public void addRepositoriesToGroup(UUID groupUUID, List<String> repoDirs) {
		if (!groupMap.containsKey(groupUUID)) {
			throw new IllegalArgumentException();
		}
		Collection<RepositoryGroup> currentGroups = groupMap
				.values();
		for (RepositoryGroup groups : currentGroups) {
			groups.removeRepositories(repoDirs);
		}

		groupMap.get(groupUUID).addRepositories(repoDirs);
		savePreferences();

	}

	public void delete(Collection<RepositoryGroup> groupsToDelete) {
		for (RepositoryGroup groupUUID : groupsToDelete) {
			preferences.remove(
					PREFS_GROUP_PREFIX + groupUUID.getUuid().toString());
			groupMap.remove(groupUUID.getUuid());
		}
		savePreferences();
	}

	private void savePreferences() {
		try {
			List<String> groupUUIDS = new ArrayList<>();
			for (RepositoryGroup group : groupMap.values()) {
				UUID uuid = group.getUuid();
				String uuidString = uuid.toString();
				groupUUIDS.add(uuidString);
				String name = group.getName();
				preferences.put(PREFS_GROUP_NAME_PREFIX + uuidString, name);
				List<String> repos = group.getRepositories();
				preferences.put(PREFS_GROUP_PREFIX + uuidString,
						StringUtils.join(repos, SEPARATOR));
			}
			preferences.put(PREFS_GROUPS,
					StringUtils.join(groupUUIDS, SEPARATOR));
			preferences.flush();
		} catch (BackingStoreException e) {
			Activator.error("error saving repository group state", e);//$NON-NLS-1$
		}
	}

	public List<RepositoryGroup> getGroups() {
		return new ArrayList<>(groupMap.values());
	}


	public boolean belongsToGroup(String repositoryName) {
		for (RepositoryGroup group : groupMap.values()) {
			if (group.getRepositories().contains(repositoryName)) {
				return true;
			}
		}
		return false;
	}

	public void removeFromGroups(List<String> reposToRemove) {
		for (RepositoryGroup group : groupMap.values()) {
			group.removeRepositories(reposToRemove);
		}
		savePreferences();
	}
}
