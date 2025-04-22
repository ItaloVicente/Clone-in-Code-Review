	/**
	 * a flag for {@link TreeEntry#accept(TreeVisitor, int)} to visit only modified entries
	 */
	public static final int MODIFIED_ONLY = 1 << 0;

	/**
	 * a flag for {@link TreeEntry#accept(TreeVisitor, int)} to visit only loaded entries
	 */
	public static final int LOADED_ONLY = 1 << 1;

	/**
	 * a flag for {@link TreeEntry#accept(TreeVisitor, int)} obsolete?
	 */
	public static final int CONCURRENT_MODIFICATION = 1 << 2;

