	/**
	 * The {@link WorkingTreeOptions} are needed to determine line endings for
	 * merged files.
	 *
	 * @since 4.11
	 */
	protected WorkingTreeOptions workingTreeOptions;

	/**
	 * The size limit (bytes) which controls a file to be stored in {@code Heap}
	 * or {@code LocalFile} during the merge.
	 */
	private int inCoreLimit;

