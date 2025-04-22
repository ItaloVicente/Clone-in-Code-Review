
	private static final boolean performanceTrace = initPerformanceTrace();

	private static boolean initPerformanceTrace() {
		String val = System.getenv(GIT_TRACE_PERFORMANCE);
		if (val == null) {
			val = System.getenv(GIT_TRACE_PERFORMANCE);
		}
		if (val != null) {
			return Boolean.valueOf(val).booleanValue();
		}
		return false;
	}

