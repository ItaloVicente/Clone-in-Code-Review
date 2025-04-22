	public long[] getTotalRequestCount() {
		AtomicLong[] hit = statHit.get();
		AtomicLong[] miss = statMiss.get();
		long[] cnt = new long[Math.max(hit.length
		for (int i = 0; i < hit.length; i++) {
			cnt[i] = hit[i].get();
		}
		for (int i = 0; i < miss.length; i++) {
			cnt[i] += miss[i].get();
		}
		return cnt;
