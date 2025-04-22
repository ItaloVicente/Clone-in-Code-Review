	private PackConfig(Config rc) {
		deltaWindow = rc.getInt("pack", "window", PackWriter.DEFAULT_DELTA_SEARCH_WINDOW_SIZE);
		deltaWindowMemory = rc.getLong("pack", null, "windowmemory", 0);
		deltaCacheSize = rc.getLong("pack", null, "deltacachesize", PackWriter.DEFAULT_DELTA_CACHE_SIZE);
		deltaCacheLimit = rc.getInt("pack", "deltacachelimit", PackWriter.DEFAULT_DELTA_CACHE_LIMIT);
		deltaDepth = rc.getInt("pack", "depth", PackWriter.DEFAULT_MAX_DELTA_DEPTH);
		compression = compression(rc);
		indexVersion = rc.getInt("pack", "indexversion", 2);
		bigFileThreshold = rc.getLong("core", null, "bigfilethreshold", PackWriter.DEFAULT_BIG_FILE_THRESHOLD);
		threads = rc.getInt("pack", "threads", 0);
