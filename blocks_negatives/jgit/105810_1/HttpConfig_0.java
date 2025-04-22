	/**
	 * Creates a {@link HttpConfig} that reads values solely from the user
	 * config.
	 *
	 * @param uri
	 *            to get the configuration values for
	 */
	public HttpConfig(URIish uri) {
		this(SystemReader.getInstance().openUserConfig(null, FS.DETECTED), uri);
	}

