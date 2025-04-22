	/**
	 * Wrapper around log4j.
	 *
	 * @param level net.spy.compat.log.AbstractLogger level.
	 * @param message object message
	 * @param e optional throwable
	 */
	@Override
	public void log(Level level, Object message, Throwable e) {
		org.apache.log4j.Level pLevel=org.apache.log4j.Level.DEBUG;
