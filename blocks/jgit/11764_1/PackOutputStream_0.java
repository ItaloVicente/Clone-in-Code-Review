	void updateProgress(int cnt) {
		writeMonitor.update(cnt);
	}

	void fixupProgress(long est) {
		long p = count - lastProgressAt;
		if (count < est)
			p += est - count;
		writeMonitor.update((int) (p >>> 10));
	}

