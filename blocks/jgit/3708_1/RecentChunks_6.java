		first(n);
	}

	private void prune() {
		while (maxBytes < curBytes) {
			Node n = lruTail;
			if (n == null)
				break;

			PackChunk c = n.chunk;
			curBytes -= c.getTotalSize();
			byKey.remove(c.getChunkKey());
			remove(n);
		}
