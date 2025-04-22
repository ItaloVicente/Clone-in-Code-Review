	/**
	 * Create a new rename detector with a specified reader and diff config.
	 *
	 * @param reader
	 *            reader to obtain objects from the repository with.
	 * @param cfg
	 *            diff config specifying rename detection options.
	 */
	public RenameDetector(ObjectReader reader, DiffConfig cfg) {
		objectReader = reader.newReader();
