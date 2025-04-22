		COULD_NOT_DELETE
	}

	/**
	 * The tree walk which we'll iterate over to merge entries.
	 *
	 * @since 3.4
	 */
	protected NameConflictTreeWalk tw;

	/**
	 * string versions of a list of commit SHA1s
	 *
	 * @since 3.0
	 */
	protected String[] commitNames;

	/**
	 * Index of the base tree within the {@link #tw tree walk}.
	 *
	 * @since 3.4
	 */
	protected static final int T_BASE = 0;

	/**
	 * Index of our tree in withthe {@link #tw tree walk}.
	 *
	 * @since 3.4
	 */
	protected static final int T_OURS = 1;

	/**
	 * Index of their tree within the {@link #tw tree walk}.
	 *
	 * @since 3.4
	 */
	protected static final int T_THEIRS = 2;

	/**
	 * Index of the index tree within the {@link #tw tree walk}.
	 *
	 * @since 3.4
	 */
	protected static final int T_INDEX = 3;

	/**
	 * Index of the working directory tree within the {@link #tw tree walk}.
	 *
	 * @since 3.4
	 */
	protected static final int T_FILE = 4;

	/**
	 * Builder to update the cache during this merge.
	 *
	 * @since 3.4
	 */
	protected DirCacheBuilder builder;

	/**
	 * merge result as tree
	 *
	 * @since 3.0
	 */
	protected ObjectId resultTree;

	/**
