	/**
	 * True if the underlying logger would allow Level.INFO through.
	 */
	@Override
	public boolean isInfoEnabled() {
		return(sunLogger.isLoggable(java.util.logging.Level.INFO));
	}
