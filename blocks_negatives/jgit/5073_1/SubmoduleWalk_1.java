	/**
	 * Get the .git directory for the current submodule entry
	 *
	 * @return .git for submodule repository
	 */
	public File getGitDirectory() {
		return getSubmoduleGitDirectory(repository, path);
	}

