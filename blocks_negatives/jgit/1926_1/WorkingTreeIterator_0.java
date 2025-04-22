	/** Buffer used to perform {@link #contentId} computations. */
	private byte[] contentReadBuffer;

	/** Digest computer for {@link #contentId} computations. */
	private MessageDigest contentDigest;

	/** File name character encoder. */
	private final CharsetEncoder nameEncoder;

