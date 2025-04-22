		if (headRef.isSymbolic()) {
			return Repository.shortenRefName(headRef.getTarget().getName());
		}
		ObjectId id = headRef.getObjectId();
		assert (id != null);
		return id.getName();
