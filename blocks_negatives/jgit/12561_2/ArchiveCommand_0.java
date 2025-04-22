	/**
	 * Available archival formats (corresponding to values for
	 * the --format= option)
	 */
	public static enum Format {
		/** Zip format */
		ZIP,

		/** Posix TAR-format */
		TAR
	}

	private static interface Archiver {
