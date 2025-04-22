	/**
	 * Highlight flag that can be applied to commits to make them stand out.
	 * <p>
	 * Allocated at the same time as {@link #currentWalk}. If the walk rebuilds,
	 * so must this flag.
	 */
	private RevFlag highlightFlag;
