	private MergeConfig(Config config) {
		fastForwardMode = FastForwardMode.FF;
		squash = false;
		commit = true;
		renames = getRenames(config);
	}
	
