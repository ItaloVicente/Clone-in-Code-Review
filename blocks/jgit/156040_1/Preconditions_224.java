	public static <T> void checkEachParameterNotNull(final String name
		if (parameters == null) {
			throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
		}
		for (final Object parameter : parameters) {
			if (parameter == null) {
				throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
			}
		}
	}
