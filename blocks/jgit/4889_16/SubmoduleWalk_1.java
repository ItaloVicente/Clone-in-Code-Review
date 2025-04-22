		return getSubmoduleRepository(parent.getWorkTree()
	}

	public static Repository getSubmoduleRepository(final File parent
			final String path) throws IOException {
		File gitDir = getSubmoduleGitDirectory(parent
		if (!gitDir.isDirectory())
