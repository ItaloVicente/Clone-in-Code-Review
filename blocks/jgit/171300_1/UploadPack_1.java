	public void setPerformanceLogHook(@Nullable PerformanceLogHook hook) {
		performanceLogHook = hook != null ? hook : PerformanceLogHook.NULL;
	}

	public PerformanceLogHook getPerformanceLogHook() {
		return performanceLogHook;
	}

