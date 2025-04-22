	 * Paths that could not be merged by this merger because of an unsolvable
	 * conflict.
	 *
	 * @since 3.4
	 */
	protected List<String> unmergedPaths = new ArrayList<>();

	/**
	 * Files modified during this merge operation.
	 *
	 * @since 3.4
	 */
	protected List<String> modifiedFiles = new LinkedList<>();

	/**
	 * If the merger has nothing to do for a file but check it out at the end of
	 * the operation, it can be added here.
	 *
	 * @since 3.4
