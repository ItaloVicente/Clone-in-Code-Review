			ChunkMeta oldMeta = chunkMeta.get(key);
			if (oldMeta != null) {
				ChunkMeta.Builder newMeta = ChunkMeta.newBuilder(oldMeta);
				newMeta.clearFragment();
				newMeta.mergeFrom(protoMeta);
				ChunkMeta meta = newMeta.build();
				dirtyMeta.put(key
