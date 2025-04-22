		for (ChunkInfo c : list) {
			int len = c.chunkSize - ChunkFormatter.TRAILER_SIZE;
			info.bytesTotal += len;
			info.objectsTotal += c.objectsTotal;
			info.objectsDelta += c.objectsOfsDelta;
			info.objectsDelta += c.objectsRefDelta;
			info.chunks.add(c.getChunkKey());
			c.getChunkKey().getChunkHash().copyRawTo(buf, 0);
