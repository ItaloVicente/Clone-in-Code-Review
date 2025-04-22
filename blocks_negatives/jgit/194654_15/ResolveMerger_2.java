	 * If the merger has nothing to do for a file but check it out at the end of
	 * the operation, it can be added here.
	 *
	 * @since 3.4
	 */
	protected Map<String, DirCacheEntry> toBeCheckedOut = new HashMap<>();

	/**
	 * Paths in this list will be deleted from the local copy at the end of the
	 * operation.
