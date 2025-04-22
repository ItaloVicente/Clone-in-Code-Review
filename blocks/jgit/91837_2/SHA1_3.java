
	public ObjectId toObjectId() {
		finish();
		return new ObjectId(h0
	}

	public void digest(MutableObjectId id) {
		finish();
		id.set(h0
	}
