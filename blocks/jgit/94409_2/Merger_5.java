	protected Repository nonNullRepo() {
		if (db == null) {
			throw new IllegalArgumentException(JGitText.get().repositoryIsRequired);
		}
		return db;
	}

