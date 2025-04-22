
		if (repo != null) {
			CoreConfig cfg = repo.getConfig().get(CoreConfig.KEY);
			bigFileThreshold = cfg.getStreamFileThreshold();
		} else {
			bigFileThreshold = 15 * 1024 * 1024;
		}
