		setMaxDeltaDepth(rc.getInt("pack", "depth", getMaxDeltaDepth())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaSearchWindowSize(rc.getInt(
				"pack", "window", getDeltaSearchWindowSize())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaSearchMemoryLimit(rc.getLong(
				"pack", "windowmemory", getDeltaSearchMemoryLimit())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaCacheSize(rc.getLong(
				"pack", "deltacachesize", getDeltaCacheSize())); //$NON-NLS-1$ //$NON-NLS-2$
		setDeltaCacheLimit(rc.getInt(
				"pack", "deltacachelimit", getDeltaCacheLimit())); //$NON-NLS-1$ //$NON-NLS-2$
		setCompressionLevel(rc.getInt("pack", "compression", //$NON-NLS-1$ //$NON-NLS-2$
				rc.getInt("core", "compression", getCompressionLevel()))); //$NON-NLS-1$ //$NON-NLS-2$
		setIndexVersion(rc.getInt("pack", "indexversion", getIndexVersion())); //$NON-NLS-1$ //$NON-NLS-2$
		setBigFileThreshold(rc.getInt(
				"core", "bigfilethreshold", getBigFileThreshold())); //$NON-NLS-1$ //$NON-NLS-2$
		setThreads(rc.getInt("pack", "threads", getThreads())); //$NON-NLS-1$ //$NON-NLS-2$
