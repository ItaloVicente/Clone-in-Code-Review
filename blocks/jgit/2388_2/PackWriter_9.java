	public boolean willInclude(final AnyObjectId id) throws IOException {
		ObjectToPack obj = objectsMap.get(id);
		if (obj != null && !obj.isEdge())
			return true;

		Set<ObjectId> toFind = Collections.singleton(id.toObjectId());
		for (CachedPack pack : cachedPacks) {
			if (pack.hasObject(toFind).contains(id))
				return true;
		}

		return false;
