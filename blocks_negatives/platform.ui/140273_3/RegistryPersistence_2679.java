	protected static final String readRequired(
			final IConfigurationElement configurationElement,
			final String attribute, final List warningsToLog,
			final String message) {
		return readRequired(configurationElement, attribute, warningsToLog,
				message, null);
