		return getChunk(objId, typeHint, false /* no load */, true /* recent */);
	}

	private ChunkAndOffset getChunk(AnyObjectId objId, int typeHint,
			boolean loadIfRequired, boolean checkRecent) throws DhtException,
			MissingObjectException {
