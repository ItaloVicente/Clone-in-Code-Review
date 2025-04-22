	/**
	 * Wrapper around sun logger.
	 *
	 * @param level net.spy.compat.log.AbstractLogger level.
	 * @param message object message
	 * @param e optional throwable
	 */
	@Override
	public void log(Level level, Object message, Throwable e) {
		java.util.logging.Level sLevel=java.util.logging.Level.SEVERE;
