	private static Function<String, Pattern> createRefPatternProvider() {
		return input -> {
			try {
				String quotedInput = Pattern.quote(input.trim());
				return Pattern.compile(
						"refs/(heads|tags|notes)/.*" + quotedInput + ".*" //$NON-NLS-1$ //$NON-NLS-2$
								+ "|" + quotedInput //$NON-NLS-1$
								+ ".*", //$NON-NLS-1$
						Pattern.CASE_INSENSITIVE);
			} catch (Exception e) {
				return null;
			}
		};
	}

