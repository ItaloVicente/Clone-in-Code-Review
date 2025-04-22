	public long[] getHitRatio() {
		AtomicLong[] hit = statHit.get();
		AtomicLong[] miss = statMiss.get();
		long[] ratio = new long[Math.max(hit.length
		for (int i = 0; i < ratio.length; i++) {
			if (i >= hit.length) {
				ratio[i] = 0;
			} else if (i >= miss.length) {
				ratio[i] = 100;
			} else {
				long hitVal = hit[i].get();
				long missVal = miss[i].get();
				long total = hitVal + missVal;
				ratio[i] = total == 0 ? 0 : hitVal * 100 / total;
			}
		}
		return ratio;
