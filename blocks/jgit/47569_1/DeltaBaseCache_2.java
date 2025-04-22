	int getMemoryUsed() {
		return curByteCount;
	}

	int getMemoryUsedByLruChainForTest() {
		int r = 0;
		for (Slot e = lruHead; e != null; e = e.lruNext) {
			r += e.size;
		}
		return r;
	}
