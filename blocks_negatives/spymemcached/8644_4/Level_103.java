	/**
	 * Debug level.
	 */
	DEBUG,
	/**
	 * Info level.
	 */
	INFO,
	/**
	 * Warning level.
	 */
	WARN,
	/**
	 * Error level.
	 */
	ERROR,
	/**
	 * Fatal level.
	 */
	FATAL;

	/**
	 * Get a string representation of this level.
	 */
	@Override
	public String toString() {
		return("{LogLevel:  " + name() + "}");
	}
