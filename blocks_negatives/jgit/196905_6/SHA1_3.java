	public static SHA1 newInstance() {
		return new SHA1();
	}

	private final State h = new State();
	private final int[] w = new int[80];
