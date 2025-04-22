		return createSyncRepoEntity("", ref); //$NON-NLS-1$
	}

	private SyncRefEntity createSyncRepoEntity(String repoName, String ref) {
		String name = PATTERN.matcher(ref).replaceFirst(""); //$NON-NLS-1$

		if (name.startsWith(repoName + "/")) //$NON-NLS-1$
			name = name.substring(repoName.length() + 1);

