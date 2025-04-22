	public static boolean toBoolean(final String stringValue) {
		if (stringValue == null)
			throw new NullPointerException(JGitText.get().expectedBooleanStringValue);

		final Boolean bool = toBooleanOrNull(stringValue);
		if (bool == null)
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().notABoolean

		return bool.booleanValue();
	}

