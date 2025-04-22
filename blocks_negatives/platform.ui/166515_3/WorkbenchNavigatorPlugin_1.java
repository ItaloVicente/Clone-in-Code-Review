
	/**
	 *
	 * @param aMessage
	 * @return A status configured with this plugin's id and the given
	 *         parameters.
	 */
	public static IStatus createWarningStatus(String aMessage) {
		return createStatus(IStatus.WARNING, 0, aMessage, null);
	}
