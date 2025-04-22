	/**
	 * Load the project paths associated with the currently checked out branch.
	 * These paths will be relative to the repository root.
	 *
	 * @return non-null but possibly empty array of projects
	 */
	public String[] getProjectPaths() {
		String branch = getBranch();
		if (branch == null)
			return new String[0];
		return getProjectPaths(branch);
	}
