	private File baseTempDir;

	public TestUtils() {
		baseTempDir = new File(rootDir, UUID.randomUUID().toString()
				.replace("-", ""));
	}

