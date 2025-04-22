	public void setPerformanceLogHook(@Nullable PerformanceLogHook hook) {
		performanceLogHook = hook != null ? hook : PerformanceLogHook.NOOP;
	}

	public PerformanceLogHook getPerformanceLogHook() {
		return performanceLogHook;
	}

