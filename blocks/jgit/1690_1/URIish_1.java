
	private static final String OPT_USER_PWD_P = "(?:([^/:@]+)(?::([^/@]+))?@)?";

	private static final String HOST_P = "([^/:]+)";

	private static final String OPT_PORT_P = "(?::(\\d+))?";

	private static final String USER_HOME_P = "(?:/~(?:[^/]+))";

	private static final String OPT_DRIVE_LETTER_P = "(?:[A-Za-z]:)?";

	private static final String RELATIVE_PATH_P = "(?:(?:[^/]+/)*[^/]+/?)";

	private static final String PATH_P = "(" + OPT_DRIVE_LETTER_P + "/?"
			+ RELATIVE_PATH_P + ")";

