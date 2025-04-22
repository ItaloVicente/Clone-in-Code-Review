
		onConfigChanged(new ConfigChangedEvent());
	}

	public void onConfigChanged(ConfigChangedEvent event) {
		CoreConfig core = config.get(CoreConfig.KEY);
		streamFileThreshold = core.getStreamFileThreshold();
