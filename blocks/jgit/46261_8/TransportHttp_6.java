	private static final int DEFAULT_MAX_REDIRECTS = 5;

	private static final int MAX_REDIRECTS = (new Supplier<Integer>() {

		@Override
		public Integer get() {
			String rawValue = SystemReader.getInstance()
			int value = DEFAULT_MAX_REDIRECTS;
			try {
				value = Integer.parseUnsignedInt(rawValue);
			} catch (NumberFormatException e) {
			}
			return Integer.valueOf(value);
		}
	}).get().intValue();

