	private Set<String> filterUntrackedFolders(Set<String> untracked
			Set<String> untrackedFolders) {
		Set<String> filtered = new TreeSet<String>();

		fileLoop: for (String file : untracked) {
			for (String folder : untrackedFolders) {
				if (file.startsWith(folder)) {
					continue fileLoop;
				}
			}

			filtered.add(file);
		}

		return filtered;
	}

