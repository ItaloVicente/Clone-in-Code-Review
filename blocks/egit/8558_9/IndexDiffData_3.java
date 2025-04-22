	private static Set<String> mergeUntrackedFolders(Set<String> oldUntrackedFolders,
			Collection<String> changedFiles, Set<String> newUntrackedFolders) {
		Set<String> merged = new HashSet<String>();
		for (String oldUntrackedFolder : oldUntrackedFolders) {
			boolean changeInUntrackedFolder = isAnyFileContainedInFolder(
					oldUntrackedFolder, changedFiles);
			if (!changeInUntrackedFolder)
				merged.add(oldUntrackedFolder);
		}
		merged.addAll(newUntrackedFolders);
		return merged;
	}

	private static boolean isAnyFileContainedInFolder(String folder,
			Collection<String> files) {
		for (String file : files)
			if (file.startsWith(folder))
				return true;
		return false;
	}

