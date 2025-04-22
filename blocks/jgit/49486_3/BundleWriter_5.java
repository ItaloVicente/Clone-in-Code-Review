
	public Statistics getStatistics() {
		return stats;
	}

	public BundleWriter setObjectCountCallback(ObjectCountCallback callback) {
		this.callback = callback;
		return this;
	}
