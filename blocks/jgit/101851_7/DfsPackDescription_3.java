	public ReftableWriter.Stats getReftableStats() {
		return refStats;
	}

	void setReftableStats(ReftableWriter.Stats stats) {
		this.refStats = stats;
		setMinUpdateIndex(stats.minUpdateIndex());
		setMaxUpdateIndex(stats.maxUpdateIndex());
		setFileSize(REFTABLE
		setBlockSize(REFTABLE
	}

