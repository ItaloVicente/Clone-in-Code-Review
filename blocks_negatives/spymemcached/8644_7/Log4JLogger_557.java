	private final org.apache.log4j.Logger l4jLogger;

	/**
	 * Get an instance of Log4JLogger.
	 */
	public Log4JLogger(String name) {
		super(name);

		l4jLogger=org.apache.log4j.Logger.getLogger(name);
	}
