	private static int positive(final String value) {
		if (value != null) {
			try {
				return Integer.parseUnsignedInt(value);
			} catch (NumberFormatException e) {
			}
		}
		return -1;
