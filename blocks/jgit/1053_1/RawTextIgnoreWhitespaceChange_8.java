	@SuppressWarnings("hiding")
	public static final Factory FACTORY = new Factory() {
		public RawText create(byte[] input) {
			return new RawTextIgnoreWhitespaceChange(input);
		}
	};
