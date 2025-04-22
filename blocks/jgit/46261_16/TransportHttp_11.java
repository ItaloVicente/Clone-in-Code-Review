
	private static final int DEFAULT_MAX_REDIRECTS = 5;

	private static final int MAX_REDIRECTS = (new Supplier<Integer>() {

		@Override
		public Integer get() {
			String rawValue = SystemReader.getInstance()
					.getProperty(MAX_REDIRECT_SYSTEM_PROPERTY);
			Integer value = Integer.valueOf(DEFAULT_MAX_REDIRECTS);
			if (rawValue != null) {
				try {
					value = Integer.valueOf(Integer.parseUnsignedInt(rawValue));
				} catch (NumberFormatException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().invalidSystemProperty
							MAX_REDIRECT_SYSTEM_PROPERTY
				}
			}
			return value;
		}
	}).get().intValue();

