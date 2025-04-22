	}

	@Override
	public boolean has(AnyObjectId objectId) {
		return unpackedObjectCache.isUnpacked(objectId)
				|| hasPackedInSelfOrAlternate(objectId)
				|| hasLooseInSelfOrAlternate(objectId);
