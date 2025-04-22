		debugOptions = options;
		GitTraceLocation.initializeFromOptions(debugOptions, isDebugging());
	}

	public DebugOptions getDebugOptions() {
		return debugOptions;
