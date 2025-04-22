		final File config = new File(new File(home, ".ssh"), Constants.CONFIG); //$NON-NLS-1$
		final OpenSshConfig osc = new OpenSshConfig(home, config);
		osc.refresh();
		return osc;
	}

	/** The user's home directory, as key files may be relative to here. */
	private final File home;

	/** The .ssh/config file we read and monitor for updates. */
	private final File configFile;

	/** Modification time of {@link #configFile} when it was last loaded. */
	private long lastModified;

	/**
	 * Encapsulates entries read out of the configuration file, and
	 * {@link Host}s created from that.
	 */
	private static class State {
		Map<String, HostEntry> entries = new LinkedHashMap<>();
		Map<String, Host> hosts = new HashMap<>();

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			return "State [entries=" + entries + ", hosts=" + hosts + "]";
		}
