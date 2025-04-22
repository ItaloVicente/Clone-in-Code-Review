		for (ChunkKey key : list) {
			ChunkInfo chunkInfo = infoByKey.get(key);
			GitStore.ChunkInfo c = chunkInfo.getData();
			int len = c.getChunkSize() - ChunkFormatter.TRAILER_SIZE;
			bytesTotal += len;
			objectsTotal += c.getObjectCounts().getTotal();
			objectsDelta += c.getObjectCounts().getOfsDelta();
			objectsDelta += c.getObjectCounts().getRefDelta();
			info.getChunkListBuilder().addChunkKey(
					chunkInfo.getChunkKey().asString());
			chunkInfo.getChunkKey().getChunkHash().copyRawTo(buf
