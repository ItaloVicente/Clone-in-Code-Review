		if (local != null) {
			final FetchConfig cfg = local.getConfig().get(FetchConfig.KEY);
			allowOfsDelta = cfg.allowOfsDelta;
		} else {
			allowOfsDelta = true;
		}
