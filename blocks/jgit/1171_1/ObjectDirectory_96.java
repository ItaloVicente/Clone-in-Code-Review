
		alternates = new AtomicReference<AlternateHandle[]>();
		if (alternatePaths != null) {
			AlternateHandle[] alt;

			alt = new AlternateHandle[alternatePaths.length];
			for (int i = 0; i < alternatePaths.length; i++)
				alt[i] = openAlternate(alternatePaths[i]);
			alternates.set(alt);
		}

		onConfigChanged(new ConfigChangedEvent());
	}

	public void onConfigChanged(ConfigChangedEvent event) {
		CoreConfig core = config.get(CoreConfig.KEY);
		streamFileThreshold = core.getStreamFileThreshold();
