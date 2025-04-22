	public <T extends Enum<?>> T getEnum(final String section
			final String subsection
		final T[] all = allValuesOf(defaultValue);
		return getEnum(all
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] allValuesOf(final T value) {
		try {
			return (T[]) value.getClass().getMethod("values").invoke(null);
		} catch (Exception err) {
			String typeName = value.getClass().getName();
			String msg = MessageFormat.format(
					JGitText.get().enumValuesNotAvailable
			throw new IllegalArgumentException(msg
		}
	}

	public <T extends Enum<?>> T getEnum(final T[] all
			final String subsection
		String value = getString(section
		if (value == null)
			return defaultValue;

		String n = value.replace(' '
		T trueState = null;
		T falseState = null;
		for (T e : all) {
			if (StringUtils.equalsIgnoreCase(e.name()
				return e;
			else if (StringUtils.equalsIgnoreCase(e.name()
				trueState = e;
			else if (StringUtils.equalsIgnoreCase(e.name()
				falseState = e;
		}

		if (trueState != null && falseState != null) {
			try {
				return StringUtils.toBoolean(n) ? trueState : falseState;
			} catch (IllegalArgumentException err) {
			}
		}

		if (subsection != null)
			throw new IllegalArgumentException(MessageFormat.format(JGitText
					.get().enumValueNotSupported3
		else
			throw new IllegalArgumentException(MessageFormat.format(JGitText
					.get().enumValueNotSupported2
	}

