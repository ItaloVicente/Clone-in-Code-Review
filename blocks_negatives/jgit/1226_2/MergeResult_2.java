	 */
	public MergeResult(ObjectId newHead, ObjectId base,
			ObjectId[] mergedCommits, MergeStatus mergeStatus,
			MergeStrategy mergeStrategy) {
		this.newHead = newHead;
		this.mergedCommits = mergedCommits;
		this.base = base;
		this.mergeStatus = mergeStatus;
		this.mergeStrategy = mergeStrategy;
	}

	/**
	 * @param newHead
	 *            the object the head points at after the merge
	 * @param base
	 *            the common base which was used to produce a content-merge. May
	 *            be <code>null</code> if the merge-result was produced without
	 *            computing a common base
	 * @param mergedCommits
	 *            all the commits which have been merged together
	 * @param mergeStatus
	 *            the status the merge resulted in
	 * @param mergeStrategy
	 *            the used {@link MergeStrategy}
