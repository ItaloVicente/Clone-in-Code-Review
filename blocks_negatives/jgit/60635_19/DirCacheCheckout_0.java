	 *
	 * @throws IOException
	 * @since 4.2
	 */
	public static void checkoutEntry(Repository repo, DirCacheEntry entry,
			ObjectReader or, boolean deleteRecursive) throws IOException {
		checkoutEntry(repo, entry, or, deleteRecursive, null);
	}

	/**
	 * Updates the file in the working tree with content and mode from an entry
	 * in the index. The new content is first written to a new temporary file in
	 * the same directory as the real file. Then that new file is renamed to the
	 * final filename.
	 *
	 * <p>
	 * <b>Note:</b> if the entry path on local file system exists as a file, it
	 * will be deleted and if it exists as a directory, it will be deleted
	 * recursively, independently if has any content.
	 * </p>
	 *
	 * <p>
	 * TODO: this method works directly on File IO, we may need another
	 * abstraction (like WorkingTreeIterator). This way we could tell e.g.
	 * Eclipse that Files in the workspace got changed
	 * </p>
	 *
	 * @param repo
	 *            repository managing the destination work tree.
	 * @param entry
	 *            the entry containing new mode and content
	 * @param or
	 *            object reader to use for checkout
	 * @param deleteRecursive
	 *            true to recursively delete final path if it exists on the file
	 *            system
	 * @param smudgeFilterCommand
	 *            the filter command to be run for smudging the entry to be
	 *            checked out
