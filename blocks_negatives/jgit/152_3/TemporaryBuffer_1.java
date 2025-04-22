	private File onDiskFile;

	/** If writing to {@link #onDiskFile} this is a buffered stream to it. */
	private OutputStream diskOut;

	/** Create a new empty temporary buffer. */
	public TemporaryBuffer() {
		inCoreLimit = DEFAULT_IN_CORE_LIMIT;
		blocks = new ArrayList<Block>(inCoreLimit / Block.SZ);
		blocks.add(new Block());
