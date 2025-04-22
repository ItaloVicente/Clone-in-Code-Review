
	private static final char[] COMMENT_CHARS = { '#'
			'^'

	public enum CleanupMode implements ConfigEnum {

		STRIP

		WHITESPACE

		VERBATIM

		SCISSORS

		DEFAULT;

		@Override
		public String toConfigValue() {
			return name().toLowerCase(Locale.ROOT);
		}

		@Override
		public boolean matchConfigValue(String in) {
			return toConfigValue().equals(in);
		}
	}

