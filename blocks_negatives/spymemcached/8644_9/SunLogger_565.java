	/**
	 * True if the underlying logger would allow Level.FINE through.
	 */
	@Override
	public boolean isDebugEnabled() {
		return(sunLogger.isLoggable(java.util.logging.Level.FINE));
	}
