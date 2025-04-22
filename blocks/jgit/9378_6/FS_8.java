	private static FSFactory factory;

	@SuppressWarnings("nls")
	public static void setFactory(FSFactory factory) {
		if (FS.factory != null)
			throw new IllegalStateException("FSFactory alread set to "
					+ FS.factory.getClass() + "
					+ factory.getClass());
		FS.factory = factory;
	}

