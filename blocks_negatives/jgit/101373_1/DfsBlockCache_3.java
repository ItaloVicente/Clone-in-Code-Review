	void remove(DfsPackFile pack) {
		packCache.remove(pack.getPackDescription());
	}

	private int slot(DfsStreamKey pack, long position) {
		return (hash(pack.hash, position) >>> 1) % tableSize;
