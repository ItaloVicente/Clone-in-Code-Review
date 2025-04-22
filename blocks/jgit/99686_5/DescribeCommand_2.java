	private ObjectId getObjectIdFromRef(Ref r) {
		ObjectId key = repo.peel(r).getPeeledObjectId();
		if (key == null) {
			key = r.getObjectId();
		}
		return key;
	}

