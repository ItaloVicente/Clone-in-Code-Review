	private MergeResult<RawText> mergeContent(CanonicalTreeParser ours
			CanonicalTreeParser theirs
			throws IOException {
		MergeResult<RawText> mergeResult = null;
		if (contentMerger != null) {
			mergeResult = contentMerger.merge(RawTextComparator.DEFAULT
					ours
		}

		if(mergeResult == null) {
			ContentMerger defaultMerger = ContentMerger.getDefaultContentMerger(db);
			mergeResult = defaultMerger.merge(RawTextComparator.DEFAULT
					ours
		}
		return mergeResult;
	}

	public void setContentMerger(ContentMerger contentMerger) {
		this.contentMerger = contentMerger;
	}

	protected static RawText getRawText(ObjectId id
