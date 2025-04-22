	private File baseTempDir;

	public TestUtils() {
		baseTempDir = new File(rootDir, System.currentTimeMillis() + "-"
				+ Integer.toHexString(System.identityHashCode(this)));
	}

