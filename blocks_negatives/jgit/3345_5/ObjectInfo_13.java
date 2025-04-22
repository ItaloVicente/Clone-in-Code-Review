	public static ObjectInfo fromBytes(ChunkKey chunkKey,
			TinyProtobuf.Decoder d, long time) {
		int typeCode = -1;
		int offset = -1;
		long packedSize = -1;
		long inflatedSize = -1;
		ObjectId deltaBase = null;
		boolean fragmented = false;

		PARSE: for (;;) {
			switch (d.next()) {
			case 0:
				break PARSE;
			case 1:
				typeCode = d.int32();
				continue;
			case 2:
				offset = d.int32();
				continue;
			case 3:
				packedSize = d.int64();
				continue;
			case 4:
				inflatedSize = d.int64();
				continue;
			case 5:
				deltaBase = d.bytesObjectId();
				continue;
			case 6:
				fragmented = d.bool();
				continue;
			default:
				d.skip();
				continue;
			}
		}

		if (typeCode < 0 || offset < 0 || packedSize < 0 || inflatedSize < 0)
			throw new IllegalArgumentException(MessageFormat.format(
					DhtText.get().invalidObjectInfo, chunkKey));

		return new ObjectInfo(chunkKey, time, typeCode, offset, //
				packedSize, inflatedSize, deltaBase, fragmented);
	}

	private final ChunkKey chunk;

	private final long time;

	private final int typeCode;

	private final int offset;

	private final long packedSize;

	private final long inflatedSize;

	private final ObjectId deltaBase;

	private final boolean fragmented;

	ObjectInfo(ChunkKey chunk, long time, int typeCode, int offset,
			long packedSize, long inflatedSize, ObjectId base,
			boolean fragmented) {
		this.chunk = chunk;
