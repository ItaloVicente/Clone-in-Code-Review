	public static <T extends Collection<?>> T checkNotEmpty(final String name
		if (parameter == null || parameter.size() == 0) {
			throw new IllegalArgumentException("Parameter named '" + name + "' should be filled!");
		}
		return parameter;
	}
