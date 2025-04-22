	private static void sqMinimal(final StringBuilder cmd, final String val) {
		if (val.matches("^[a-zA-Z0-9._/-]*$")) {
			cmd.append(val);
		} else {
			sq(cmd, val);
		}
	}

	private static void sqAlways(final StringBuilder cmd, final String val) {
		sq(cmd, val);
	}

	private static void sq(final StringBuilder cmd, final String val) {
		if (val.length() > 0)
			cmd.append(QuotedString.BOURNE.quote(val));
	}

