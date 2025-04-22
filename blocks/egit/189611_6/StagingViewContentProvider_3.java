	Collection<StagingFolderEntry> getUntrackedFileFolders() {
		Set<StagingFolderEntry> folders = new HashSet<>();
		for (StagingEntry entry : content) {
			if (!entry.isTracked() && !entry.isStaged()) {
				folders.add(entry.getParent());
			}
		}
		return folders;
	}

