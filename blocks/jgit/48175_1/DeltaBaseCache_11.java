	int getMemoryUsedByLruChainForTest() {
		int r = 0;
		for (Entry e = lruHead; e != null; e = e.lruNext) {
			r += e.data.length;
		}
		return r;
	}
