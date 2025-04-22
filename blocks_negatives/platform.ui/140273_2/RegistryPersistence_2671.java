	 * Reads the parameterized command from a parent configuration element. This
	 * is used to read the parameter sub-elements from a key element, as well as
	 * the command id. Each parameter is guaranteed to be valid. If invalid
	 * parameters are found, then a warning status will be appended to the
	 * <code>warningsToLog</code> list. The command id is required, or a
	 * warning will be logged.
