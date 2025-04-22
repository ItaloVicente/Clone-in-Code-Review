	@NonNull
	public ContentMergeStrategy getContentMergeStrategy() {
		return strategy;
	}

	public void setContentMergeStrategy(ContentMergeStrategy strategy) {
		this.strategy = strategy == null ? ContentMergeStrategy.CONFLICT
				: strategy;
	}

