	/**
	 * Does the content merge. The three texts base, ours and theirs are
	 * specified with {@link CanonicalTreeParser}. If any of the parsers is
	 * specified as <code>null</code> then an empty text will be used instead.
	 *
	 * @param base
	 * @param ours
	 * @param theirs
	 *
	 * @return the result of the content merge
	 * @throws IOException
	 */
	private MergeResult<RawText> contentMerge(CanonicalTreeParser base,
			CanonicalTreeParser ours, CanonicalTreeParser theirs)
			throws IOException {
		RawText baseText = base == null ? RawText.EMPTY_TEXT : getRawText(
				base.getEntryObjectId(), db);
		RawText ourText = ours == null ? RawText.EMPTY_TEXT : getRawText(
				ours.getEntryObjectId(), db);
		RawText theirsText = theirs == null ? RawText.EMPTY_TEXT : getRawText(
				theirs.getEntryObjectId(), db);
		return (mergeAlgorithm.merge(RawTextComparator.DEFAULT, baseText,
				ourText, theirsText));
	}
