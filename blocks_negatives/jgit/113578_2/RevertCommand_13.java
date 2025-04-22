	 *
	 * @return on success the {@link RevCommit} pointed to by the new HEAD is
	 *         returned. If a failure occurred during revert <code>null</code>
	 *         is returned. The list of successfully reverted {@link Ref}'s can
	 *         be obtained by calling {@link #getRevertedRefs()}
	 * @throws GitAPIException
	 * @throws WrongRepositoryStateException
	 * @throws ConcurrentRefUpdateException
	 * @throws UnmergedPathsException
	 * @throws NoMessageException
