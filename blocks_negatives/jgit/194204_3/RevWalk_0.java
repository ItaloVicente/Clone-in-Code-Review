	/**
	 * Set on a RevCommit when a {@link TreeRevFilter} has been applied.
	 * <p>
	 * This flag is processed by the {@link RewriteGenerator} to check if a
	 * {@link TreeRevFilter} has been applied.
	 *
	 * @see TreeRevFilter
	 * @see RewriteGenerator
	 */
	static final int TREE_REV_FILTER_APPLIED = 1 << 7;

