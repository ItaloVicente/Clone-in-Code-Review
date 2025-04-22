	private boolean contentMerge(CanonicalTreeParser base,
			CanonicalTreeParser ours, CanonicalTreeParser theirs)
			throws FileNotFoundException, IllegalStateException, IOException {
		MergeFormatter fmt = new MergeFormatter();

		RawText baseText = base == null ? RawText.EMPTY_TEXT : getRawText(
				base.getEntryObjectId(), db);

		MergeResult<RawText> result = mergeAlgorithm.merge(
				RawTextComparator.DEFAULT, baseText,
				getRawText(ours.getEntryObjectId(), db),
				getRawText(theirs.getEntryObjectId(), db));
