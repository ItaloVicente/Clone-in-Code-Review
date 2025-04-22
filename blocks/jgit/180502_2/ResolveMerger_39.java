	@NonNull
	public ContentMergeStrategy getContentMergeStrategy() {
		return contentStrategy;
	}

	public void setContentMergeStrategy(ContentMergeStrategy strategy) {
		contentStrategy = strategy == null ? ContentMergeStrategy.CONFLICT
				: strategy;
	}

