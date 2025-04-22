	public void setContinuousTimeout(boolean timedOut) {
		if (timedOut && isActive()) {
			continuousTimeout.incrementAndGet();
		} else {
			continuousTimeout.set(0);
		}
	}

	public int getContinuousTimeout() {
		return continuousTimeout.get();
	}


