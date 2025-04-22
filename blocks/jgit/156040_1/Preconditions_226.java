	public static String checkNotEmpty(final String name
		if (parameter == null || parameter.trim().length() == 0) {
			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
		}
		return parameter;
	}
