	private final String username;
	private final char[] password;

	/**
	 * Construct a plain callback handler with the given username and password.
	 *
	 * @param u the username
	 * @param p the password
	 */
	public PlainCallbackHandler(String u, String p) {
		username=u;
		password=p.toCharArray();
	}
