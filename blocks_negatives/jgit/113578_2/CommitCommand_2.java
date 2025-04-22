	 *
	 * @return a {@link RevCommit} object representing the successful commit.
	 * @throws NoHeadException
	 *             when called on a git repo without a HEAD reference
	 * @throws NoMessageException
	 *             when called without specifying a commit message
	 * @throws UnmergedPathsException
	 *             when the current index contained unmerged paths (conflicts)
	 * @throws ConcurrentRefUpdateException
	 *             when HEAD or branch ref is updated concurrently by someone
	 *             else
	 * @throws WrongRepositoryStateException
	 *             when repository is not in the right state for committing
	 * @throws AbortedByHookException
	 *             if there are either pre-commit or commit-msg hooks present in
	 *             the repository and one of them rejects the commit.
