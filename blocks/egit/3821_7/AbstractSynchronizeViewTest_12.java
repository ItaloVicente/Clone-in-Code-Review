package org.eclipse.egit.core.synchronize;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.core.CoreText;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.osgi.util.NLS;

class GitSyncObjectCache {

	private final String name;

	private final DiffEntry diffEntry;

	private Map<String, GitSyncObjectCache> members;

	public GitSyncObjectCache() {
		this("", null); //$NON-NLS-1$
	}

	GitSyncObjectCache(String name, DiffEntry diffEntry) {
		this.name = name;
		this.diffEntry = diffEntry;
	}

	public String getName() {
		return name;
	}

	public DiffEntry getDiffEntry() {
		return diffEntry;
	}

	public void addMember(DiffEntry entry) {
		String memberPath = getMemberPath(entry);

		if (members == null)
			members = new HashMap<String, GitSyncObjectCache>();

		int start = -1;
		Map<String, GitSyncObjectCache> parent = members;
		int separatorIdx = memberPath.indexOf("/"); //$NON-NLS-1$
		while (separatorIdx > 0) {
			String key = memberPath.substring(start + 1, separatorIdx);
			GitSyncObjectCache cacheObject = parent.get(key);
			if (cacheObject == null)
				throw new RuntimeException(
						NLS.bind(CoreText.GitSyncObjectCache_noData, key));

			start = separatorIdx;
			separatorIdx = memberPath.indexOf("/", separatorIdx + 1); //$NON-NLS-1$
			if (cacheObject.members == null)
				cacheObject.members = new HashMap<String, GitSyncObjectCache>();

			parent = cacheObject.members;
		}

		String newName;
		if (start > 0)
			newName = memberPath.substring(start + 1);
		else
			newName = memberPath;

		GitSyncObjectCache obj = new GitSyncObjectCache(newName, entry);
		parent.put(newName, obj);
	}

	public GitSyncObjectCache get(String childPath) {
		if (childPath.length() == 0)
			return this;

		int start = -1;
		Map<String, GitSyncObjectCache> parent = members;
		int separatorIdx = childPath.indexOf("/"); //$NON-NLS-1$
		while (separatorIdx > 0) {
			String key = childPath.substring(start + 1, separatorIdx);
			GitSyncObjectCache childObject = parent.get(key);
			if (childObject == null)
				return null;

			start = separatorIdx;
			separatorIdx = childPath.indexOf("/", separatorIdx + 1); //$NON-NLS-1$
			parent = childObject.members;
		}

		return parent.get(childPath.subSequence(
				childPath.lastIndexOf("/") + 1, childPath.length())); //$NON-NLS-1$
	}

	public int membersCount() {
		return members != null ? members.size() : 0;
	}

	public Collection<GitSyncObjectCache> members() {
		return members != null ? members.values() : null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("entry: ").append(diffEntry).append("\n"); //$NON-NLS-1$ //$NON-NLS-2$
		if (members != null) {
			builder.append("members: "); //$NON-NLS-1$
			for (GitSyncObjectCache obj : members.values())
				builder.append(obj.toString()).append("\n"); //$NON-NLS-1$
		}

		return builder.toString();
	}

	private String getMemberPath(DiffEntry entry) {
		if (!entry.getNewPath().equals(DiffEntry.DEV_NULL))
			return entry.getNewPath();
		else
			return entry.getOldPath();
	}

}
