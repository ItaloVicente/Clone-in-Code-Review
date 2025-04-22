		if (curSize < maxSize) {
			n = new Node();
			curSize++;
		} else {
			n = lruTail;
			byKey.remove(n.chunk.getChunkKey());
		}
