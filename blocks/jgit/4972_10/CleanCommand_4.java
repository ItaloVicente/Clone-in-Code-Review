	private Set<String> filterIgnorePaths(Set<String> inputPaths
			Set<String> ignoredNotInIndex
		if (ignore) {
			Set<String> filtered = new TreeSet<String>(inputPaths);
			for (String path : inputPaths)
				for (String ignored : ignoredNotInIndex)
					if ((exact && path.equals(ignored))
							|| (!exact && path.startsWith(ignored))) {
						filtered.remove(path);
						break;
					}

			return filtered;
		}
		return inputPaths;
	}

	private Set<String> filterFolders(Set<String> untracked
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

