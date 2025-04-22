	 * @since 3.0
	 */
	protected WorkingTreeIterator workingTreeIterator;

	/**
	 * our merge algorithm
	 * @since 3.0
	 */
	protected MergeAlgorithm mergeAlgorithm;

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

	/**
	 * The {@link ContentMergeStrategy} to use for "resolve" and "recursive"
	 * merges.
	 */
	@NonNull
	private ContentMergeStrategy contentStrategy = ContentMergeStrategy.CONFLICT;

	/**
	 * Keeps {@link CheckoutMetadata} for {@link #checkout()}.
	 */
	private Map<String, CheckoutMetadata> checkoutMetadata;

	/**
	 * Keeps {@link CheckoutMetadata} for {@link #cleanUp()}.
	 */
	private Map<String, CheckoutMetadata> cleanupMetadata;

	private static MergeAlgorithm getMergeAlgorithm(Config config) {
		SupportedAlgorithm diffAlg = config.getEnum(
				CONFIG_DIFF_SECTION, null, CONFIG_KEY_ALGORITHM,
				HISTOGRAM);
		return new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
	}

	private static int getInCoreLimit(Config config) {
		return config.getInt(
				ConfigConstants.CONFIG_MERGE_SECTION, ConfigConstants.CONFIG_KEY_IN_CORE_LIMIT, 10 << 20);
	}

	private static String[] defaultCommitNames() {
		return new String[] { "BASE", "OURS", "THEIRS" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private static final Attributes NO_ATTRIBUTES = new Attributes();

	/**
	 * Constructor for ResolveMerger.
	 *
