		PackConfig packConfig = new PackConfig();

		if (configFile != null) {
			if (!configFile.exists()) {
				throw die(MessageFormat.format(
						CLIText.get().configFileNotFound
						configFile.getAbsolutePath()));
			}

			FileBasedConfig cfg = new FileBasedConfig(configFile
			cfg.load();

			WindowCacheConfig wcc = new WindowCacheConfig();
			wcc.fromConfig(cfg);
			WindowCache.reconfigure(wcc);

			packConfig.fromConfig(cfg);
		}

		int threads = packConfig.getThreads();
		if (threads <= 0)
			threads = Runtime.getRuntime().availableProcessors();
		if (1 < threads)
			packConfig.setExecutor(Executors.newFixedThreadPool(threads));

