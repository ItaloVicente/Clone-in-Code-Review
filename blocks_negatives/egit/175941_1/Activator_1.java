	@Override
	public void optionsChanged(DebugOptions options) {
		debugOptions = options;
		GitTraceLocation.initializeFromOptions(options, isDebugging());
	}

	/**
	 * @return the {@link DebugOptions}
	 */
	public DebugOptions getDebugOptions() {
		return debugOptions;
	}

