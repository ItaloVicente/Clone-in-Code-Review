	/**
	 * Search for object id with the specified start offset in associated pack
	 * (reverse) index.
	 *
	 * @param ctx
	 *            current reader for the calling thread.
	 * @param offset
	 *            start offset of object to find
	 * @return object id for this offset, or null if no object was found
	 * @throws IOException
	 *             the index file cannot be loaded into memory.
	 */
	ObjectId findObjectForOffset(DfsReader ctx, long offset) throws IOException {
		return getReverseIdx(ctx).findObject(offset);
	}

