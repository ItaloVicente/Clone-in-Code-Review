	public static <T> T checkNotNull(final String name
		if (parameter == null) {
			throw new IllegalArgumentException("Parameter named '" + name + "' should be not null!");
		}
		return parameter;
	}
