	private Repository getRepository() {
		if (db == null)
			throw new IllegalStateException("Repository has not been set"); //$NON-NLS-1$
		return db;
	}

