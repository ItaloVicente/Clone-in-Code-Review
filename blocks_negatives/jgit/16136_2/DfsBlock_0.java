	/**
	 * Size in bytes to pass to {@link Inflater} at a time.
	 * <p>
	 * Blocks can be large (for example 1 MiB), while compressed objects inside
	 * of them are very small (for example less than 100 bytes for a delta). JNI
	 * forces the data supplied to the Inflater to be copied during setInput(),
	 * so use a smaller stride to reduce the risk that too much unnecessary was
	 * moved into the native layer.
	 */
	private static final int INFLATE_STRIDE = 512;

