	 * @param user
	 *            username to authenticate as. If null a reasonable default must
	 *            be selected by the implementation. This may be
	 *            <code>System.getProperty("user.name")</code>.
	 * @param pass
	 *            optional user account password or passphrase. If not null a
	 *            UserInfo that supplies this value to the SSH library will be
	 *            configured.
	 * @param host
	 *            hostname (or IP address) to connect to. Must not be null.
	 * @param port
	 *            port number the server is listening for connections on. May be <=
	 *            0 to indicate the IANA registered port of 22 should be used.
