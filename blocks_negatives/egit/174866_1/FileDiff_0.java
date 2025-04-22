	/**
	 * Comparator for sorting FileDiffs based on getPath(). Compares first the
	 * directory part, if those are equal, the filename part.
	 */
	public static final Comparator<FileDiff> PATH_COMPARATOR = Comparator
			.comparing(FileDiff::getPath, PATH_STRING_COMPARATOR);

