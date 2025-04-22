	private static final int OVERHEAD = 32;
	private static final OperationStatus STORED =
		new OperationStatus(true, "STORED");
	protected final String type;
	protected final String key;
	protected final int flags;
	protected final int exp;
	protected final byte[] data;
