	/** Modification time of {@link #configFile} when {@link #hosts} loaded. */
	private long lastModified;

	/** Cached entries read out of the configuration file. */
	private Map<String, Host> hosts;

	OpenSshConfig(final File h, final File cfg) {
		home = h;
		configFile = cfg;
		hosts = Collections.emptyMap();
