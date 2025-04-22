	/**
	 * Return the merge base of two commits.
	 * <p>
	 * May only be called after {@link #merge(RevCommit...)}.
	 *
	 * @param aIdx
	 *            index of the first commit in tips passed to
	 *            {@link #merge(RevCommit...)}.
	 * @param bIdx
	 *            index of the second commit in tips passed to
	 *            {@link #merge(RevCommit...)}.
	 * @return the merge base of two commits
	 * @throws IncorrectObjectTypeException
	 *             one of the input objects is not a commit.
	 * @throws IOException
	 *             objects are missing or multiple merge bases were found.
	 * @deprecated use {@link #getBaseCommitId()} instead, as that does not
	 *             require walking the commits again
	 */
	@Deprecated
	public RevCommit getBaseCommit(final int aIdx, final int bIdx)
			throws IncorrectObjectTypeException,
			IOException {
		if (sourceCommits[aIdx] == null)
			throw new IncorrectObjectTypeException(sourceObjects[aIdx],
					Constants.TYPE_COMMIT);
		if (sourceCommits[bIdx] == null)
			throw new IncorrectObjectTypeException(sourceObjects[bIdx],
					Constants.TYPE_COMMIT);
		return getBaseCommit(sourceCommits[aIdx], sourceCommits[bIdx]);
	}

