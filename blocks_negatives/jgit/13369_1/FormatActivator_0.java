	private static final List<String> myFormats = new ArrayList<String>();

	private static final void register(String name, ArchiveCommand.Format<?> fmt) {
		myFormats.add(name);
		ArchiveCommand.registerFormat(name, fmt);
	}

	/**
	 * Register all included archive formats so they can be used
	 * as arguments to the ArchiveCommand.setFormat() method.
	 *
	 * Should not be called twice without a call to stop() in between.
	 * Not thread-safe.
	 */
	public static void start() {
		register("tar", new TarFormat());
		register("tgz", new TgzFormat());
		register("txz", new TxzFormat());
		register("zip", new ZipFormat());
	}

	/**
	 * Clean up by deregistering all formats that were registered
	 * by start().
	 *
	 * Not thread-safe.
	 */
	public static void stop() {
		for (String name : myFormats) {
			ArchiveCommand.unregisterFormat(name);
		}
		myFormats.clear();
	}

