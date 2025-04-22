	public DfsGarbageCollector setReftableConfig(ReftableConfig cfg) {
		reftableConfig = cfg;
		return this;
	}

	public DfsGarbageCollector setReftableInitialMinUpdateIndex(long u) {
		reftableInitialMinUpdateIndex = Math.max(u
		return this;
	}

	public DfsGarbageCollector setReftableBootstrapMaxUpdateIndex(long u) {
		reftableInitialMaxUpdateIndex = Math.max(0
		return this;
	}

