		static ObjectId create(RefData.Id src) {
			if (src.hasChunkKey()) {
				return new IdWithChunk(
						ObjectId.fromString(src.getObjectName()),
						ChunkKey.fromString(src.getChunkKey()));
			}
			return ObjectId.fromString(src.getObjectName());
		}

