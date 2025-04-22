	 * @param repo    the {@link org.eclipse.jgit.lib.Repository}.
	 * @param dirCache if set, use the provided dir cache. Otherwise, creates a new one
	 * @param oi       to use for writing the modified objects with.
	 * @return an IO handler.
	 */
	public static WorkTreeUpdater createInCoreWorkTreeUpdater(Repository repo, DirCache dirCache,
			ObjectInserter oi) {
