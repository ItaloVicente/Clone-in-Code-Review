	public LargeObjectException(AnyObjectId id) {
		setObjectId(id);
	}

	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(AnyObjectId id) {
		if (objectId == null)
			objectId = id.copy();
	}

	@Override
	public String getMessage() {
		return objectId != null ? objectId.name() : getClass().getSimpleName();
