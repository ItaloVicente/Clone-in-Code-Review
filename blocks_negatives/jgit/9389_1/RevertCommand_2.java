
	/**
	 * @return the list of successfully reverted {@link Ref}'s. Never
	 *         <code>null</code> but maybe an empty list if no commit was
	 *         successfully cherry-picked
	 */
	public List<Ref> getRevertedRefs() {
		return revertedRefs;
	}

	/**
	 * @return the result of the merge failure, <code>null</code> if no merge
	 *         failure occurred during the revert
	 */
	public MergeResult getFailingResult() {
		return failingResult;
	}

	/**
	 * @return the unmerged paths, will be null if no merge conflicts
	 */
	public List<String> getUnmergedPaths() {
		return unmergedPaths;
	}
