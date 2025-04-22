	private AtomicLong getStat(AtomicReference<AtomicLong[]> stats
		while (true) {
			AtomicLong[] vals = stats.get();
			if (vals.length > pos) {
				return vals[pos];
			}
			AtomicLong[] expect = vals;
			vals = new AtomicLong[pos];
			for (int i = 0; i < vals.length; i++) {
				if (i < expect.length) {
					vals[i] = expect[i];
				} else {
					vals[i] = new AtomicLong();
				}
			}
			if (stats.compareAndSet(expect
				return vals[pos];
			}
		}
	}

