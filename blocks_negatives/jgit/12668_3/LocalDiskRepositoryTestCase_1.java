	private static void reportDeleteFailure(final String testName,
			final boolean failOnError, final File e) {
		final String severity;
		if (failOnError)
			severity = "ERROR";
		else
			severity = "WARNING";

		final String msg = severity + ": Failed to delete " + e + " in "
				+ testName;
