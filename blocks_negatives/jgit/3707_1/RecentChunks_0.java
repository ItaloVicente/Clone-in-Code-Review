		for (Node n = lruHead; n != null; n = n.next) {
			if (key.equals(n.chunk.getChunkKey())) {
				hit(n);
				stats.recentChunks_Hits++;
				return n.chunk;
			}
