
		if (isDebugging()) {
			ServiceTracker debugTracker = new ServiceTracker(context,
					DebugOptions.class.getName(), null);
			debugTracker.open();

			DebugOptions opts = (DebugOptions) debugTracker.getService();
			GitTraceLocation.initializeFromOptions(opts, true);
		}
