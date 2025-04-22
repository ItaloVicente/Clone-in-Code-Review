	/**
	 * Returns a command object to execute a {@code init} command
	 *
	 * @see <a
	 *      >Git documentation about init</a>
	 * @return a {@link InitCommand} used to collect all optional parameters and
	 *         to finally execute the {@code init} command
	 */
	static public InitCommand init() {
		return new InitCommand();
	}

