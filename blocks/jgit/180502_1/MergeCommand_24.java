	public MergeCommand setContentMergeStrategy(ContentMergeStrategy strategy) {
		checkCallable();
		this.contentStrategy = strategy;
		return this;
	}

