	private boolean exclude(AnyObjectId objectId) {
		if (excludeInPacks == null)
			return false;
		if (excludeInPackLast.hasObject(objectId))
			return true;
		for (PackIndex idx : excludeInPacks) {
			if (idx.hasObject(objectId)) {
				excludeInPackLast = idx;
				return true;
			}
		}
		return false;
	}

