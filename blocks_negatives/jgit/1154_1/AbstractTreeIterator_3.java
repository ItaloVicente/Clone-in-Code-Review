
	/**
	 * @return
	 * 			  True if this iterator encountered a .gitignore file when initializing entries.
	 * 			  Checks if the gitIgnoreTimeStamp > 0.
	 */
	public boolean hasGitIgnore() {
		return gitIgnoreTimeStamp > 0;
	}

	/**
	 * @return
	 * 			  Last modified time of the .gitignore file, if any. Will be > 0 if a .gitignore
	 * 			  exists.
	 */
	public long getGitIgnoreLastModified() {
		return gitIgnoreTimeStamp;
	}
