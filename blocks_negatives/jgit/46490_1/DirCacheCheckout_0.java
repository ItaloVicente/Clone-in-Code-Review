	/**
	 * Updates the file in the working tree with content and mode from an entry
	 * in the index. The new content is first written to a new temporary file in
	 * the same directory as the real file. Then that new file is renamed to the
	 * final filename. Use this method only for checkout of a single entry.
	 * Otherwise use
	 * {@code checkoutEntry(Repository, File f, DirCacheEntry, ObjectReader)}
	 * instead which allows to reuse one {@code ObjectReader} for multiple
	 * entries.
	 *
	 * <p>
	 * TODO: this method works directly on File IO, we may need another
	 * abstraction (like WorkingTreeIterator). This way we could tell e.g.
	 * Eclipse that Files in the workspace got changed
	 * </p>
	 *
	 * @param repository
	 * @param f
	 *            this parameter is ignored.
	 * @param entry
	 *            the entry containing new mode and content
	 * @throws IOException
	 * @deprecated Use the overloaded form that accepts {@link ObjectReader}.
	 */
	@Deprecated
	public static void checkoutEntry(final Repository repository, File f,
			DirCacheEntry entry) throws IOException {
		ObjectReader or = repository.newObjectReader();
		try {
			checkoutEntry(repository, f, entry, or);
		} finally {
			or.release();
		}
	}

	/**
	 * Updates the file in the working tree with content and mode from an entry
	 * in the index. The new content is first written to a new temporary file in
	 * the same directory as the real file. Then that new file is renamed to the
	 * final filename.
	 *
	 * <p>
	 * TODO: this method works directly on File IO, we may need another
	 * abstraction (like WorkingTreeIterator). This way we could tell e.g.
	 * Eclipse that Files in the workspace got changed
	 * </p>
	 *
	 * @param repo
	 * @param f
	 *            this parameter is ignored.
	 * @param entry
	 *            the entry containing new mode and content
	 * @param or
	 *            object reader to use for checkout
	 * @throws IOException
	 * @deprecated Do not pass File object.
	 */
	@Deprecated
	public static void checkoutEntry(final Repository repo, File f,
			DirCacheEntry entry, ObjectReader or) throws IOException {
		if (f == null || repo.getWorkTree() == null)
			throw new IllegalArgumentException();
		if (!f.equals(new File(repo.getWorkTree(), entry.getPathString())))
			throw new IllegalArgumentException();
		checkoutEntry(repo, entry, or);
	}

