		return new SHA1();
	}

	private final State h = new State();
	private final int[] w = new int[80];

	/** Buffer to accumulate partial blocks to 64 byte alignment. */
	private final byte[] buffer = new byte[64];

	/** Total number of bytes in the message. */
	private long length;

	private boolean detectCollision = DETECT_COLLISIONS;
	private boolean foundCollision;

	private final int[] w2 = new int[80];
	private final State state58 = new State();
	private final State state65 = new State();
	private final State hIn = new State();
	private final State hTmp = new State();

	private SHA1() {
		h.init();
	}

	/**
	 * Enable likely collision detection.
	 * <p>
	 * Default is {@code true}.
	 * <p>
	 * May also be set by system property:
	 * {@code -Dorg.eclipse.jgit.util.sha1.detectCollision=true}.
	 *
	 * @param detect
	 *            a boolean.
	 * @return {@code this}
	 */
	public SHA1 setDetectCollision(boolean detect) {
		detectCollision = detect;
		return this;
