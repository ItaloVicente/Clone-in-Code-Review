	public PullCommand mergeWith(ContentMerger newMerger) {
		checkCallable();
		this.contentMerger = newMerger;
		return this;
	}

