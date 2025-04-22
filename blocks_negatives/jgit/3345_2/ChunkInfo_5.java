	/** @return source of this chunk. */
	public Source getSource() {
		return source;
	}

	/** @return type of object in the chunk, or {@link #OBJ_MIXED}. */
	public int getObjectType() {
		return objectType;
	}

	/** @return true if this chunk is part of a large fragmented object. */
	public boolean isFragment() {
		return fragment;
	}

	/** @return cached pack this is a member of, or null. */
	public CachedPackKey getCachedPack() {
		return cachedPack;
	}

	/** @return size of the chunk's compressed data, in bytes. */
	public int getChunkSizeInBytes() {
		return chunkSize;
	}

	/** @return size of the chunk's index data, in bytes. */
	public int getIndexSizeInBytes() {
		return indexSize;
	}

	/** @return size of the chunk's meta data, in bytes. */
	public int getMetaSizeInBytes() {
		return metaSize;
	}

	/** @return number of objects stored in the chunk. */
	public int getObjectsTotal() {
		return objectsTotal;
	}

	/** @return number of whole objects stored in the chunk. */
	public int getObjectsWhole() {
		return objectsWhole;
	}

	/** @return number of OFS_DELTA objects stored in the chunk. */
	public int getObjectsOffsetDelta() {
		return objectsOfsDelta;
	}

	/** @return number of REF_DELTA objects stored in the chunk. */
	public int getObjectsReferenceDelta() {
		return objectsRefDelta;
	}

	/**
	 * Convert this link into a byte array for storage.
	 *
	 * @return the link data, encoded as a byte array. This does not include the
	 *         ChunkKey, callers must store that separately.
	 */
	public byte[] asBytes() {
		return asBytes(this);
