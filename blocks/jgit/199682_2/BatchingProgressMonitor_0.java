
	private static boolean performanceTrace = false;

	static {
		SystemReader sr = SystemReader.getInstance();
		String val = sr.getProperty(GIT_TRACE_PERFORMANCE);
		if (val == null) {
			val = sr.getenv(GIT_TRACE_PERFORMANCE);
		}
		if (val != null) {
			performanceTrace = Boolean.valueOf(val).booleanValue();
		}

	}

