	/**
	 * Sets list of already existing tags. This list will be loaded in
	 * <code>Details</code> section of this dialog.
	 *
	 * @param existingTags
	 */
	public void setExistingTags(List<RevTag> existingTags) {
		this.existingTags = existingTags;
	}

	/**
	 * Sets list of existing commits. This list will be loaded in
	 * {@link CommitCombo} widget in <code>Advanced</code> section of this
	 * dialog.
	 *
	 * @param revCommits
	 */
	public void setRevCommitList(RevWalk revCommits) {
		this.revCommits = revCommits;
	}

