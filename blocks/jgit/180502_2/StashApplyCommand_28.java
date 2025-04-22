	public StashApplyCommand setContentMergeStrategy(
			ContentMergeStrategy strategy) {
		checkCallable();
		this.contentStrategy = strategy;
		return this;
	}

