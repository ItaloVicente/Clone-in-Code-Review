		return deltaBase;
	}

	/**
	 * Convert this ObjectInfo into a byte array for storage.
	 *
	 * @return the ObjectInfo data, encoded as a byte array. This does not
	 *         include the ChunkKey, callers must store that separately.
	 */
	public byte[] asBytes() {
		TinyProtobuf.Encoder e = TinyProtobuf.encode(256);
		e.int32(1, typeCode);
		e.int32(2, offset);
		e.int64(3, packedSize);
		e.int64(4, inflatedSize);
		e.bytes(5, deltaBase);
		if (fragmented)
			e.bool(6, fragmented);
		return e.asByteArray();
