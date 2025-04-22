	public static <T> T[] checkNotEmpty(final String name
		if (parameter == null || parameter.length == 0) {
			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
		}
		return parameter;
	}
