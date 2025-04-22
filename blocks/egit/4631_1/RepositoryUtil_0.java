	private Set<String> getRepositories() {
		String dirs;
		synchronized (prefs) {
			dirs = prefs.get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
		}
		if (dirs == null || dirs.length() == 0)
			return Collections.emptySet();
		Set<String> configuredStrings = new HashSet<String>();
		StringTokenizer tok = new StringTokenizer(dirs, File.pathSeparator);
		while (tok.hasMoreTokens())
			configuredStrings.add(tok.nextToken());
		return configuredStrings;
	}

