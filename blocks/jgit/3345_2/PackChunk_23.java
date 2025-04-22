						baseChunk = ChunkMetaUtil.getBaseChunk(
								pc.key
								pc.meta
								base);
						baseChunkKey = ChunkKey.fromString(baseChunk.getChunkKey());
						basePosInChunk = (int) (baseChunk.getRelativeStart() - base);
