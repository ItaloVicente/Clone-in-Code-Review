	private boolean removeDir(String dir) {
		Set<String> dirStrings = new HashSet<String>();
		dirStrings.addAll(getConfiguredRepositories());
		if (dirStrings.remove(dir)) {
			saveDirs(dirStrings);
			return true;
		}
		return false;
	}

