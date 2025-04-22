			Attributes attributes)
			throws IOException {
		RawText baseText;
		RawText ourText;
		RawText theirsText;

		try {
			baseText = base == null ? RawText.EMPTY_TEXT : getRawText(
							base.getEntryObjectId(), attributes);
			ourText = ours == null ? RawText.EMPTY_TEXT : getRawText(
							ours.getEntryObjectId(), attributes);
			theirsText = theirs == null ? RawText.EMPTY_TEXT : getRawText(
							theirs.getEntryObjectId(), attributes);
		} catch (BinaryBlobException e) {
			MergeResult<RawText> r = new MergeResult<>(Collections.<RawText>emptyList());
			r.setContainsConflicts(true);
			return r;
		}
		return (mergeAlgorithm.merge(RawTextComparator.DEFAULT, baseText,
				ourText, theirsText));
