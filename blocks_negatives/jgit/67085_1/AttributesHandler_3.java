	 * Merges the matching GLOBAL attributes for an entry path.
	 *
	 * @param entryPath
	 *            the path to test. The path must be relative to this attribute
	 *            node's own repository path, and in repository path format
	 *            (uses '/' and not '\').
	 * @param isDirectory
	 *            true if the target item is a directory.
	 * @param result
	 *            that will hold the attributes matching this entry path. This
	 *            method will NOT override any existing entry in attributes.
	 */
	private void mergeGlobalAttributes(String entryPath, boolean isDirectory,
			Attributes result) {
		mergeAttributes(globalNode, entryPath, isDirectory, result);
	}

	/**
	 * Merges the matching INFO attributes for an entry path.
	 *
	 * @param entryPath
	 *            the path to test. The path must be relative to this attribute
	 *            node's own repository path, and in repository path format
	 *            (uses '/' and not '\').
	 * @param isDirectory
	 *            true if the target item is a directory.
	 * @param result
	 *            that will hold the attributes matching this entry path. This
	 *            method will NOT override any existing entry in attributes.
	 */
	private void mergeInfoAttributes(String entryPath, boolean isDirectory,
			Attributes result) {
		mergeAttributes(infoNode, entryPath, isDirectory, result);
	}

	/**
	 * Merges the matching working directory attributes for an entry path.
