	private byte[] open(ObjectReader reader, FileMode mode,
			AbbreviatedObjectId id) throws IOException {
		if (mode == FileMode.MISSING)
			return new byte[] {};

		if (mode.getObjectType() != Constants.OBJ_BLOB)
			return new byte[] {};

		if (!id.isComplete()) {
			Collection<ObjectId> ids = reader.resolve(id);
			if (ids.size() == 1)
				id = AbbreviatedObjectId.fromObjectId(ids.iterator().next());
			else if (ids.size() == 0)
				throw new MissingObjectException(id, Constants.OBJ_BLOB);
			else
				throw new AmbiguousObjectException(id, ids);
		}

		ObjectLoader ldr = reader.open(id.toObjectId());
		return ldr.getCachedBytes(bigFileThreshold);
	}

