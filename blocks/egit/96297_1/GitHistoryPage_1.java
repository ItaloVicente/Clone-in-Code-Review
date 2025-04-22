	private AnyObjectId resolveFetchHead(Repository db) {
		try {
			return db.resolve(Constants.FETCH_HEAD);
		} catch (IOException e) {
			return null;
		}
	}

