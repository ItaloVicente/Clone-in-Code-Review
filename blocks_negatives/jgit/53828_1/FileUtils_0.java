	/**
	 * Create a symbolic link
	 *
	 * @param path
	 * @param target
	 * @throws IOException
	 * @since 3.0
	 */
	public static void createSymLink(File path, String target)
			throws IOException {
		FS.DETECTED.createSymLink(path, target);
	}

	/**
	 * @param path
	 * @return the target of the symbolic link, or null if it is not a symbolic
	 *         link
	 * @throws IOException
	 * @since 3.0
	 */
	public static String readSymLink(File path) throws IOException {
		return FS.DETECTED.readSymLink(path);
	}

