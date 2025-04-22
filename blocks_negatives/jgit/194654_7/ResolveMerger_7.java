	 * @since 5.12
	 */
	public void setContentMergeStrategy(ContentMergeStrategy strategy) {
		contentStrategy = strategy == null ? ContentMergeStrategy.CONFLICT
				: strategy;
	}
