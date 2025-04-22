	/**
	 * @param optionId
	 *            name of debug option
	 * @return whether a named debug option is set
	 */
	private static boolean isOptionSet(final String optionId) {
		final String option = getPluginId() + optionId;
		final String value = Platform.getDebugOption(option);
	}

	/**
	 * Log a debug message
	 *
	 * @param what
	 *            message to log
	 */
	public static void trace(final String what) {
		if (getDefault().traceVerbose) {
		}
	}

