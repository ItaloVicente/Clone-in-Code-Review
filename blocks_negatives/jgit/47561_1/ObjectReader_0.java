	/**
	 * Advice from a {@link RevWalk} that a walk is starting from these roots.
	 *
	 * @param walk
	 *            the revision pool that is using this reader.
	 * @param roots
	 *            starting points of the revision walk. The starting points have
	 *            their headers parsed, but might be missing bodies.
	 * @throws IOException
	 *             the reader cannot initialize itself to support the walk.
	 */
	public void walkAdviceBeginCommits(RevWalk walk, Collection<RevCommit> roots)
			throws IOException {
	}

	/**
	 * Advice from an {@link ObjectWalk} that trees will be traversed.
	 *
	 * @param ow
	 *            the object pool that is using this reader.
	 * @param min
	 *            the first commit whose root tree will be read.
	 * @param max
	 *            the last commit whose root tree will be read.
	 * @throws IOException
	 *             the reader cannot initialize itself to support the walk.
	 */
	public void walkAdviceBeginTrees(ObjectWalk ow, RevCommit min, RevCommit max)
			throws IOException {
	}

	/** Advice from that a walk is over. */
	public void walkAdviceEnd() {
	}

