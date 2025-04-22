	private String createUniqueTestFolderPrefix() {
		return "test" + (System.currentTimeMillis() + "_" + (testCount++));
	}

	protected File createTempDirectory(String name) throws IOException {
		String gitdirName = createUniqueTestFolderPrefix();
		File parent = new File(trash
		File directory = new File(parent
		return directory.getCanonicalFile();
	}

