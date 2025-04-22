	public DebugOptions getDebugOptions() {
		if (debugTracker == null) {
			if (bundleContext == null)
				return null;
			debugTracker = new ServiceTracker(bundleContext,
					DebugOptions.class.getName(), null);
			debugTracker.open();
		}
		return (DebugOptions) debugTracker.getService();
	}

	public DebugTrace getTrace() {
		if (trace == null) {
			trace = getDebugOptions().newDebugTrace(PI_WORKBENCH_RENDERERS);
		}
		return trace;
	}

	public static boolean isTracing(String option) {
		final DebugOptions debugOptions = activator.getDebugOptions();
		return debugOptions.isDebugEnabled()
				&& debugOptions.getBooleanOption(PI_WORKBENCH_RENDERERS
						+ option, false);
	}

	public static void trace(String option, String msg, Throwable error) {
		if (isTracing(option)) {
			System.out.println(msg);
			if (error != null) {
				error.printStackTrace(System.out);
			}
		}
		activator.getTrace().trace(option, msg, error);
	}

