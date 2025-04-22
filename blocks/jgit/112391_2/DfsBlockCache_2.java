	private static AtomicLong[] newCounters() {
		AtomicLong[] ret = new AtomicLong[PackExt.values().length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new AtomicLong();
		}
		return ret;
	}

	private static AtomicLong getStat(AtomicReference<AtomicLong[]> stats
			DfsStreamKey key) {
		int pos = key.packExtPos;
		while (true) {
			AtomicLong[] vals = stats.get();
			if (pos < vals.length) {
				return vals[pos];
			}
			AtomicLong[] expect = vals;
			vals = new AtomicLong[Math.max(pos + 1
			System.arraycopy(expect
			for (int i = expect.length; i < vals.length; i++) {
				vals[i] = new AtomicLong();
			}
			if (stats.compareAndSet(expect
				return vals[pos];
			}
		}
	}

	private static long[] getStatVals(AtomicReference<AtomicLong[]> stat) {
		AtomicLong[] stats = stat.get();
		long[] cnt = new long[stats.length];
		for (int i = 0; i < stats.length; i++) {
			cnt[i] = stats[i].get();
		}
		return cnt;
	}

