
	public MergeCommand mergeWith(ContentMerger newMerger) {
		checkCallable();
		this.contentMerger = newMerger;
		return this;
	}
