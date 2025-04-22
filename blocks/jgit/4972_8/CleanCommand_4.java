	private Set<String> filterUntrackedFolders(Set<String> untracked
			Set<String> untrackedFolders) {
		Set<String> filtered = new TreeSet<String>(untracked);
		for (String file : untracked)
			for (String folder : untrackedFolders)
				if (file.startsWith(folder)) {
					filtered.remove(file);
					break;
				}

		return filtered;
	}

