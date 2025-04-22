	/**
	 * Adds a directory to the list if it is not already there
	 *
	 * @param file
	 * @return see {@link Collection#add(Object)}
	 */
	public static boolean addDir(File file) {

		String dirString;
		try {
			dirString = file.getCanonicalPath();
		} catch (IOException e) {
			dirString = file.getAbsolutePath();
		}

		List<String> dirStrings = getDirs();
		if (dirStrings.contains(dirString)) {
