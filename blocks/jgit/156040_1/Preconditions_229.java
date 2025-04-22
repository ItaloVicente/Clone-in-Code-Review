	public static <T> T checkInstanceOf(final String name
		checkNotNull(name
		checkNotNull("clazz"
		if (!clazz.isInstance(parameter)) {
			throw new IllegalArgumentException(
					"Parameter named '" + name + "' is not instance of clazz '" + clazz.getName() + "'!");
		}
