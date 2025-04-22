

	private String createUniqueTestFolderPrefix() {
		return "test" + (System.currentTimeMillis() + "_" + (testCount++));
	}

	protected File createTempDirectory(String name) throws IOException {
		String gitdirName = createUniqueTestFolderPrefix();
		File parent = new File(trash
		File directory = new File(parent
		return directory.getCanonicalFile();
	}

	protected File createUniqueTestGitDir(boolean bare) throws IOException {
		String gitdirName = createUniqueTestFolderPrefix();
		if (!bare)
			gitdirName += "/";
		gitdirName += Constants.DOT_GIT;
		File gitdir = new File(trash
		return gitdir.getCanonicalFile();
	}

	protected File createTempFile() throws IOException {
		return new File(trash
	}

