	public static interface Factory {
		RawText create(byte[] input);
	}

	public static final Factory FACTORY = new Factory() {
		public RawText create(byte[] input) {
			return new RawText(input);
		}
	};

