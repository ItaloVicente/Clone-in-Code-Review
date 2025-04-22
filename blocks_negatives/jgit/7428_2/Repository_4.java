	public boolean hasObject(AnyObjectId objectId) {
		try {
			return getObjectDatabase().has(objectId);
		} catch (IOException e) {
			return false;
		}
	}
