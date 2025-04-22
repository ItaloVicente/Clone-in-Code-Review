	public DebugOptions getDebugOptions() {
		if (debugTracker == null) {
			if (context == null) {
				return null;
			}
			debugTracker = new ServiceTracker<>(context, DebugOptions.class, null);
			debugTracker.open();
		}
		return debugTracker.getService();
