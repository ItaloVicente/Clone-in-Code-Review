	}

	@Override
	public boolean has(AnyObjectId objectId) {
		return unpackedObjectCache.isUnpacked(objectId)
				|| hasPacked(objectId)
				|| hasLoose(objectId);
