
		ChunkMeta.Builder metaBuilder = ChunkMeta.newBuilder();
		for (ChunkKey k : fragmentList)
			metaBuilder.addFragment(k.asString());
		ChunkMeta meta = metaBuilder.build();

