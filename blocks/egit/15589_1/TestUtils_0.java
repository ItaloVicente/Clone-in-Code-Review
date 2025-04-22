	private static File customUserHome() {
		final String home = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					public String run() {
						return System.getProperty("custom.user.home"); //$NON-NLS-1$
					}
				});
		if (home == null || home.length() == 0)
			return null;
		return new File(home).getAbsoluteFile();
	}

	private File rootDir;

	public TestUtils() {
		File userHome = customUserHome();
		if (userHome == null)
			userHome = FS.DETECTED.userHome();
		rootDir = new File(userHome, "EGitTestTempDir");
	}

