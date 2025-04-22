
	@Override
	public Repository getRepository() {
		return input.getRepository();
	}

	@Override
	public ObjectId getObjectId() {
		return getNewId();
	}
