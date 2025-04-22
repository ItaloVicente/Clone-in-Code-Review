	protected ObjectId getBaseObjectId() {
		return baseId;
	}

	@Override
	protected ObjectId getRemoteObjectId() {
		return remoteId;
	}

	private boolean objectExist(RevCommit commit, ObjectId id) {
		return commit != null && id != null && !id.equals(zeroId());
	}

