		if (headRef.isSymbolic()) {
			return Repository.shortenRefName(headRef.getTarget().getName());
		}
		ObjectId id = headRef.getObjectId();
		if (id == null) {
			throw new NullPointerException();
		}
		return id.getName();
