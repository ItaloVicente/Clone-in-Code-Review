	public long getHitCount() {
		return statHit.get();
	}

	public long getMissCount() {
		return statMiss.get();
	}

	public long getTotalRequestCount() {
		return getHitCount() + getMissCount();
	}

