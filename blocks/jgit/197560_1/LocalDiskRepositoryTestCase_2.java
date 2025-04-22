		db.create(options.bare());
		if (options.useRefCache) {
			FileBasedConfig cfg = db.getConfig();
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_REFCACHE
			cfg.save();
			RepositoryCache.close(db);
			db = new FileRepository(gitdir);
		}
		if (options.autoClose()) {
