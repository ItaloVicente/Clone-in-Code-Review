
	public CherryPickCommand mergeWith(ContentMerger newMerger) {
		checkCallable();
		this.contentMerger = newMerger;
		return this;
	}
