	/**
	 * @return false if <code>git-daemon-export-ok</code> is required to export
	 *         a repository; true if <code>git-daemon-export-ok</code> is
	 *         ignored.
	 * @see #setExportAll(boolean)
	 */
	public boolean isExportAll() {
		return exportAll;
	}

	/**
	 * Set whether or not to export all repositories.
	 * <p>
	 * If false (the default), repositories must have a
	 * <code>git-daemon-export-ok</code> file to be accessed through this
	 * daemon.
	 * <p>
	 * If true, all repositories are available through the daemon, whether or
	 * not <code>git-daemon-export-ok</code> exists.
	 *
	 * @param export
	 */
	public void setExportAll(final boolean export) {
		exportAll = export;
	}

	/**
	 * Add a single repository to the set that is exported by this daemon.
	 * <p>
	 * The existence (or lack-thereof) of <code>git-daemon-export-ok</code> is
	 * ignored by this method. The repository is always published.
	 *
	 * @param name
	 *            name the repository will be published under.
	 * @param db
	 *            the repository instance.
	 */
	public void exportRepository(String name, final Repository db) {
		if (!name.endsWith(Constants.DOT_GIT_EXT))
			name = name + Constants.DOT_GIT_EXT;
		exports.put(name, db);
		RepositoryCache.register(db);
	}

	/**
	 * Recursively export all Git repositories within a directory.
	 *
	 * @param dir
	 *            the directory to export. This directory must not itself be a
	 *            git repository, but any directory below it which has a file
	 *            named <code>git-daemon-export-ok</code> will be published.
	 */
	public void exportDirectory(final File dir) {
		exportBase.add(dir);
	}

