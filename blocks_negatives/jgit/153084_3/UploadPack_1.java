		PackConfig cfg = packConfig;
		if (cfg == null)
			cfg = new PackConfig(db);
		final PackWriter pw = new PackWriter(cfg, walk.getObjectReader(),
				accumulator);
		try {
