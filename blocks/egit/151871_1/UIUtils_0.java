	private static Function<String, Pattern> createRefPatternProvider() {
		return input -> {
			try {
				return Pattern.compile(".*\\Q" + input.trim() + "\\E.*", //$NON-NLS-1$ //$NON-NLS-2$
						Pattern.CASE_INSENSITIVE);
			} catch (Exception e) {
				return null;
			}
		};
	}

