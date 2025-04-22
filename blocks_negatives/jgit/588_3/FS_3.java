	public static File userHome() {
		return USER_HOME.home;
	}

	private static class USER_HOME {
		static final File home = INSTANCE.userHomeImpl();
