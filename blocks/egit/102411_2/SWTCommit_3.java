
	@Override
	public Repository getRepository() {
		return walk != null ? walk.getRepository() : null;
	}

	@Override
	public RevCommit getRevCommit() {
		return this;
	}
