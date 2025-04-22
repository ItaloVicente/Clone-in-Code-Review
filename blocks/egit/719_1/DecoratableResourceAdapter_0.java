package org.eclipse.egit.ui;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tag;

public class RepositoryUtil {

	private final Map<String, Map<String, String>> commitMappingCache = new HashMap<String, Map<String, String>>();

	private final Map<String, String> repositoryNameCache = new HashMap<String, String>();

	RepositoryUtil() {
	}

	void dispose() {
		commitMappingCache.clear();
		repositoryNameCache.clear();
	}

	public String mapCommitToRef(Repository repository, String commitId,
			boolean refresh) {

		synchronized (commitMappingCache) {

			if (!ObjectId.isId(commitId)) {
				return null;
			}

			Map<String, String> cacheEntry = commitMappingCache.get(repository
					.getDirectory().toString());
			if (!refresh && cacheEntry != null
					&& cacheEntry.containsKey(commitId)) {
				return cacheEntry.get(commitId);
			}
			if (cacheEntry == null) {
				cacheEntry = new HashMap<String, String>();
				commitMappingCache.put(repository.getDirectory().getPath(),
						cacheEntry);
			} else {
				cacheEntry.clear();
			}

			Map<String, Date> tagMap = new HashMap<String, Date>();
			try {
				Map<String, Ref> tags = repository.getRefDatabase().getRefs(
						Constants.R_TAGS);
				for (Ref tagRef : tags.values()) {
					Tag tag = repository.mapTag(tagRef.getName());
					if (tag.getObjId().name().equals(commitId)) {
						Date timestamp;
						if (tag.getTagger() != null) {
							timestamp = tag.getTagger().getWhen();
						} else {
							timestamp = null;
						}
						tagMap.put(tagRef.getName(), timestamp);
					}
				}
			} catch (IOException e) {
			}

			String cacheValue = null;

			if (!tagMap.isEmpty()) {
				Date compareDate = new Date(0);
				for (Map.Entry<String, Date> tagEntry : tagMap.entrySet()) {
					if (tagEntry.getValue() != null
							&& tagEntry.getValue().after(compareDate)) {
						compareDate = tagEntry.getValue();
						cacheValue = tagEntry.getKey();
					}
				}
				if (cacheValue == null) {
					String compareString = ""; //$NON-NLS-1$
					for (String tagName : tagMap.keySet()) {
						if (tagName.compareTo(compareString) >= 0) {
							cacheValue = tagName;
							compareString = tagName;
						}
					}
				}
			}

			if (cacheValue == null) {
				Set<String> branchNames = new TreeSet<String>();
				try {
					Map<String, Ref> remoteBranches = repository
							.getRefDatabase().getRefs(Constants.R_HEADS);
					for (Ref branch : remoteBranches.values()) {
						if (branch.getObjectId().name().equals(commitId)) {
							branchNames.add(branch.getName());
						}
					}
				} catch (IOException e) {
				}
				if (!branchNames.isEmpty()) {
					cacheValue = branchNames.toArray(new String[branchNames
							.size()])[branchNames.size() - 1];
				}
			}

			if (cacheValue == null) {
				Set<String> branchNames = new TreeSet<String>();
				try {
					Map<String, Ref> remoteBranches = repository
							.getRefDatabase().getRefs(Constants.R_REMOTES);
					for (Ref branch : remoteBranches.values()) {
						if (branch.getObjectId().name().equals(commitId)) {
							branchNames.add(branch.getName());
						}
					}
					if (!branchNames.isEmpty()) {
						cacheValue = branchNames.toArray(new String[branchNames
								.size()])[branchNames.size() - 1];
					}
				} catch (IOException e) {
				}
			}
			cacheEntry.put(commitId, cacheValue);
			return cacheValue;
		}
	}

	public String getRepositoryName(Repository repository) {
		synchronized (repositoryNameCache) {
			File gitDir = repository.getDirectory();
			if (gitDir != null) {
				String name = repositoryNameCache.get(gitDir.getPath()
						.toString());
				if (name != null) {
					return name;
				}
				name = gitDir.getParentFile().getName();
				repositoryNameCache.put(gitDir.getPath().toString(), name);
				return name;
			}
		}
		return ""; //$NON-NLS-1$
	}

}
