		this.reader = reader;
		if (reader instanceof ObjectReuseAsIs)
			reuseSupport = ((ObjectReuseAsIs) reader);
		else
			reuseSupport = null;

		final PackConfig pc = configOf(repo).get(PackConfig.KEY);
		deltaSearchWindowSize = pc.deltaWindow;
		deltaSearchMemoryLimit = pc.deltaWindowMemory;
		deltaCacheSize = pc.deltaCacheSize;
		deltaCacheLimit = pc.deltaCacheLimit;
		maxDeltaDepth = pc.deltaDepth;
		compressionLevel = pc.compression;
		indexVersion = pc.indexVersion;
		bigFileThreshold = pc.bigFileThreshold;
		threads = pc.threads;
	}

	private static Config configOf(final Repository repo) {
		if (repo == null)
			return new Config();
		return repo.getConfig();
	}

	/**
	 * Check whether object is configured to reuse deltas existing in
	 * repository.
	 * <p>
	 * Default setting: {@link #DEFAULT_REUSE_DELTAS}
	 * </p>
	 *
	 * @return true if object is configured to reuse deltas; false otherwise.
	 */
	public boolean isReuseDeltas() {
		return reuseDeltas;
