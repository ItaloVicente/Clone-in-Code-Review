	public ReftableConfig getReftableConfig() {
		ReftableConfig cfg = new ReftableConfig();
		cfg.fromConfig(getRepository().getConfig());
		return cfg;
	}

