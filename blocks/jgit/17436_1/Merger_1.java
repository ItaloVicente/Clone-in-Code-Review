		if (base == null) {
			baseCommitId = null;
			return new EmptyTreeIterator();
		} else {
			baseCommitId = base.toObjectId();
			return openTree(base.getTree());
		}
	}

	public ObjectId getBaseCommitId() {
		return baseCommitId;
