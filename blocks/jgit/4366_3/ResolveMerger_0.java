	private MergeResult<RawText> contentMerge(CanonicalTreeParser base
			CanonicalTreeParser ours
			throws IOException {
		RawText baseText = base == null ? RawText.EMPTY_TEXT : getRawText(
				base.getEntryObjectId()
		RawText ourText = ours == null ? RawText.EMPTY_TEXT : getRawText(
				ours.getEntryObjectId()
		RawText theirsText = theirs == null ? RawText.EMPTY_TEXT : getRawText(
				theirs.getEntryObjectId()
		return (mergeAlgorithm.merge(RawTextComparator.DEFAULT
				ourText
	}

