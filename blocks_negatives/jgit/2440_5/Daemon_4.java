		name = name.substring(1);

		Repository db;
		db = exports.get(name.endsWith(Constants.DOT_GIT_EXT) ? name : name
				+ Constants.DOT_GIT_EXT);
		if (db != null) {
			db.incrementOpen();
			return db;
		}

		for (final File baseDir : exportBase) {
			final File gitdir = FileKey.resolve(new File(baseDir, name), FS.DETECTED);
			if (gitdir != null && canExport(gitdir))
				return openRepository(gitdir);
		}
		return null;
	}

	private static Repository openRepository(final File gitdir) {
		try {
			return RepositoryCache.open(FileKey.exact(gitdir, FS.DETECTED));
		} catch (IOException err) {
