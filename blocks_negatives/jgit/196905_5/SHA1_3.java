	public static SHA1 newInstance() {
		return new SHA1();
	}

	private final State h = new State();
	private final int[] w = new int[80];

	/** Buffer to accumulate partial blocks to 64 byte alignment. */
	private final byte[] buffer = new byte[64];
