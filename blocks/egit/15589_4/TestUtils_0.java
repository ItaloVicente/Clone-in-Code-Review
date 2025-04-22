	private final static File rootDir = customTestDirectory();

	private static File customTestDirectory() {
		final String p = System.getProperty("egit.test.tmpdir"); //$NON-NLS-1$
		File testDir = null;
		boolean isDefault = true;
		if (p == null || p.length() == 0)
			testDir = new File(FS.DETECTED.userHome(), "egit.test.tmpdir"); //$NON-NLS-1$
		else {
			isDefault = false;
			testDir = new File(p).getAbsoluteFile();
		}
		System.out.println("egit.test.tmpdir" //$NON-NLS-1$
				+ (isDefault ? "[default]: " : ": ") //$NON-NLS-1$ $NON-NLS-2$
				+ testDir.getAbsolutePath());
		return testDir;
	}

