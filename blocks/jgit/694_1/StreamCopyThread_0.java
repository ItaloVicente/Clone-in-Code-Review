
	private boolean needFlush() {
		int i = flushCounter.get();
		if (i > 0) {
			flushCounter.decrementAndGet();
			return true;
		}
		return false;
	}
