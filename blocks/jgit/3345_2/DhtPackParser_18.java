	private static ChunkMeta.Builder edit(ChunkMeta meta) {
		if (meta != null)
			return ChunkMeta.newBuilder(meta);
		return ChunkMeta.newBuilder();
	}

