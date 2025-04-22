	private static File customTestDirectory() {
		final String testDir = System.getProperty("egit.test.tmpdir"); //$NON-NLS-1$
		if (testDir == null || testDir.length() == 0)
			return null;
		return new File(testDir).getAbsoluteFile();
	}

	private File rootDir;

	public TestUtils() {
		File testDir = customTestDirectory();
		if (testDir == null) {
			testDir = FS.DETECTED.userHome();
			rootDir = new File(testDir, "egit.test.tmpdir");
		} else
			rootDir = testDir;
	}

