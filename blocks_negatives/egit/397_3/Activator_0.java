	private static boolean isOptionSet(final String optionId) {
		final String option = getPluginId() + optionId;
		final String value = Platform.getDebugOption(option);
	}

	/**
	 * Utility method for debug logging.
	 *
	 * @param what
	 */
	public static void trace(final String what) {
		if (getDefault().traceVerbose) {
		}
	}

	private boolean traceVerbose;

