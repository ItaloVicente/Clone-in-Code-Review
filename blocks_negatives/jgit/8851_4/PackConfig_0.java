		setMaxDeltaDepth(rc.getInt("pack", "depth", getMaxDeltaDepth()));
		setDeltaSearchWindowSize(rc.getInt("pack", "window", getDeltaSearchWindowSize()));
		setDeltaSearchMemoryLimit(rc.getLong("pack", "windowmemory", getDeltaSearchMemoryLimit()));
		setDeltaCacheSize(rc.getLong("pack", "deltacachesize", getDeltaCacheSize()));
		setDeltaCacheLimit(rc.getInt("pack", "deltacachelimit", getDeltaCacheLimit()));
		setCompressionLevel(rc.getInt("pack", "compression",
				rc.getInt("core", "compression", getCompressionLevel())));
		setIndexVersion(rc.getInt("pack", "indexversion", getIndexVersion()));
		setBigFileThreshold(rc.getInt("core", "bigfilethreshold", getBigFileThreshold()));
		setThreads(rc.getInt("pack", "threads", getThreads()));
