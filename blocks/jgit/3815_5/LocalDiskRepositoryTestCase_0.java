	private String createUniqueTestFolderPrefix() {
		return "test" + (System.currentTimeMillis() + "_" + (testCount++));
	}

	protected File createTempDirectory(String name) throws IOException {
		String gitdirName = createUniqueTestFolderPrefix();
		File parent = new File(trash
		File directory = new File(parent
		FileUtils.mkdirs(directory);
		return directory.getCanonicalFile();
	}

