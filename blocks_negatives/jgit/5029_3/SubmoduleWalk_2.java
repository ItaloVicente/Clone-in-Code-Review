	/**
	 * Does the current submodule entry have a .git directory in the parent
	 * repository's working tree?
	 *
	 * @return true if .git directory exists, false otherwise
	 */
	public boolean hasGitDirectory() {
		return getGitDirectory().isDirectory();
	}

