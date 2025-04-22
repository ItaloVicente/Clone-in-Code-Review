		return dirs;
	}

	private Set<String> migrateAbolutePaths() {
		String dirString;
		Set<String> dirs;
		dirString = prefs.get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
		dirs = toDirSet(dirString);
		saveDirs(dirs);
		return dirs;
	}

	private Set<String> toDirSet(String dirs) {
		if (dirs == null || dirs.isEmpty()) {
