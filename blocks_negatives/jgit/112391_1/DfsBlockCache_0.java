	/** @return 0..100, defining number of cache hits. */
	public long getHitRatio() {
		long hits = statHit.get();
		long miss = statMiss.get();
		long total = hits + miss;
		if (total == 0)
			return 0;
		return hits * 100 / total;
