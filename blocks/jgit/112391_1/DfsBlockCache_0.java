	public long[] getHitCount() {
		AtomicLong[] stats = statHit.get();
		long[] cnt = new long[stats.length];
		for (int i = 0; i < stats.length; i++) {
			cnt[i] = stats[i].get();
		}
		return cnt;
