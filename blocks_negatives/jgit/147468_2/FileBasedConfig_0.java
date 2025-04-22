		load(true);
	}

	/**
	 * Load the configuration as a Git text style configuration file.
	 * <p>
	 * If the file does not exist, this configuration is cleared, and thus
	 * behaves the same as though the file exists, but is empty.
	 *
	 * @param useFileSnapshotWithConfig
	 *            if {@code true} use the FileSnapshot with config, otherwise
	 *            use it without config
	 * @throws IOException
	 *             if IO failed
	 * @throws ConfigInvalidException
	 *             if config is invalid
	 * @since 5.1.9
	 */
	public void load(boolean useFileSnapshotWithConfig)
			throws IOException, ConfigInvalidException {
