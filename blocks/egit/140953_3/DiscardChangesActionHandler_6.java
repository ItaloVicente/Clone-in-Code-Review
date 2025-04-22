	private boolean haveChanges(Map<Repository, Collection<String>> paths) {
		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		for (Map.Entry<Repository, Collection<String>> entry : paths
				.entrySet()) {
			Repository repo = entry.getKey();
			Assert.isNotNull(repo);
			IndexDiffCacheEntry indexDiff = cache.getIndexDiffCacheEntry(repo);
			if (indexDiff == null) {
				return true; // No info, assume worst case
			}
			IndexDiffData diff = indexDiff.getIndexDiff();
			if (diff == null || hasChanges(diff, entry.getValue())) {
				return true;
			}
		}
		return false;
	}

	private boolean hasChanges(@NonNull IndexDiffData diff,
			Collection<String> paths) {
		Set<String> repoPaths = new HashSet<>(paths);
		if (repoPaths.contains("")) { //$NON-NLS-1$
			return diff.hasChanges();
		}
		if (containsAny(repoPaths, diff.getAdded())
				|| containsAny(repoPaths, diff.getChanged())
				|| containsAny(repoPaths, diff.getModified())
				|| containsAny(repoPaths, diff.getRemoved())) {
			return true;
		}
		if (hasDirectories) {
			return containsAnyDirectory(repoPaths, diff.getAdded())
					|| containsAnyDirectory(repoPaths, diff.getChanged())
					|| containsAnyDirectory(repoPaths, diff.getModified())
					|| containsAnyDirectory(repoPaths, diff.getRemoved());
		}
		return false;
	}

	private boolean containsAny(Set<String> repoPaths,
			Collection<String> files) {
		return files.stream().anyMatch(repoPaths::contains);
	}

	private boolean containsAnyDirectory(Set<String> repoPaths,
			Collection<String> files) {
		String lastDirectory = null;
		for (String file : files) {
			int j = file.lastIndexOf('/');
			if (j <= 0) {
				continue;
			}
			String directory = file.substring(0, j);
			String withTerminator = directory + '/';
			if (lastDirectory != null
					&& lastDirectory.startsWith(withTerminator)) {
				continue;
			}
			if (repoPaths.contains(directory)) {
				return true;
			}
			lastDirectory = withTerminator;
			for (int i = directory.indexOf('/'); i > 0; i = directory.indexOf(
					'/', i + 1)) {
				if (repoPaths.contains(directory.substring(0, i))) {
					return true;
				}
			}
		}
		return false;
	}

