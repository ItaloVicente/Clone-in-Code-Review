	protected Repository nonNullRepo() {
		if (db == null) {
			throw new NullPointerException(JGitText.get().repositoryIsRequired);
		}
		return db;
	}

