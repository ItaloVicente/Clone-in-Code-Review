
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (evictionCount ^ (evictionCount >>> 32));
		result = prime * result + (int) (hitCount ^ (hitCount >>> 32));
		result = prime * result
				+ (int) (loadFailureCount ^ (loadFailureCount >>> 32));
		result = prime * result
				+ (int) (loadSuccessCount ^ (loadSuccessCount >>> 32));
		result = prime * result + (int) (missCount ^ (missCount >>> 32));
		result = prime * result
				+ (int) (openByteCount ^ (openByteCount >>> 32));
		result = prime * result
				+ (int) (openFileCount ^ (openFileCount >>> 32));
		result = prime * result
				+ (int) (totalLoadTime ^ (totalLoadTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		WindowCacheStats other = (WindowCacheStats) obj;
		if (evictionCount != other.evictionCount) {
			return false;
		}
		if (hitCount != other.hitCount) {
			return false;
		}
		if (loadFailureCount != other.loadFailureCount) {
			return false;
		}
		if (loadSuccessCount != other.loadSuccessCount) {
			return false;
		}
		if (missCount != other.missCount) {
			return false;
		}
		if (openByteCount != other.openByteCount) {
			return false;
		}
		if (openFileCount != other.openFileCount) {
			return false;
		}
		if (totalLoadTime != other.totalLoadTime) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "WindowCacheStats [hitCount=" + hitCount + "
				+ missCount + "
				+ "
				+ totalLoadTime + "
				+ "
				+ openByteCount + "]";
	}

	@Override
	public void resetCounters() {
		WindowCache.getInstance().resetStats();
	}
