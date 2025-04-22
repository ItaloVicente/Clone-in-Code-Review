	 * @throws IOException
	 */
	private MergeResult<RawText> contentMerge(CanonicalTreeParser base,
			CanonicalTreeParser ours, CanonicalTreeParser theirs,
			Attributes[] attributes, ContentMergeStrategy strategy)
			throws BinaryBlobException, IOException {
		RawText baseText = base == null ? RawText.EMPTY_TEXT
				: getRawText(base.getEntryObjectId(), attributes[T_BASE]);
		RawText ourText = ours == null ? RawText.EMPTY_TEXT
				: getRawText(ours.getEntryObjectId(), attributes[T_OURS]);
		RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
				: getRawText(theirs.getEntryObjectId(), attributes[T_THEIRS]);
		mergeAlgorithm.setContentMergeStrategy(strategy);
		return mergeAlgorithm.merge(RawTextComparator.DEFAULT, baseText,
				ourText, theirsText);
	}

	private boolean isIndexDirty() {
		if (inCore)
			return false;

		final int modeI = tw.getRawMode(T_INDEX);
		final int modeO = tw.getRawMode(T_OURS);

		final boolean isDirty = nonTree(modeI)
				&& !(modeO == modeI && tw.idEqual(T_INDEX, T_OURS));
		if (isDirty)
			failingPaths
					.put(tw.getPathString(), MergeFailureReason.DIRTY_INDEX);
		return isDirty;
	}

	private boolean isWorktreeDirty(WorkingTreeIterator work,
			DirCacheEntry ourDce) throws IOException {
		if (work == null)
			return false;

		final int modeF = tw.getRawMode(T_FILE);
		final int modeO = tw.getRawMode(T_OURS);

		boolean isDirty;
		if (ourDce != null)
			isDirty = work.isModified(ourDce, true, reader);
		else {
			isDirty = work.isModeDifferent(modeO);
			if (!isDirty && nonTree(modeF))
				isDirty = !tw.idEqual(T_FILE, T_OURS);
		}

		if (isDirty && modeF == FileMode.TYPE_TREE
				&& modeO == FileMode.TYPE_MISSING)
			isDirty = false;
		if (isDirty)
			failingPaths.put(tw.getPathString(),
					MergeFailureReason.DIRTY_WORKTREE);
		return isDirty;
	}

	/**
