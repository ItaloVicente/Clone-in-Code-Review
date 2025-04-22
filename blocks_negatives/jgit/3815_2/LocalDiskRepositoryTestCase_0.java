	/**
	 * Creates a new unique directory for a test repository
	 *
	 * @param bare
	 *            true for a bare repository; false for a repository with a
	 *            working directory
	 * @return a unique directory for a test repository
	 * @throws IOException
	 */
	protected File createUniqueTestGitDir(boolean bare) throws IOException {
		String uniqueId = System.currentTimeMillis() + "_" + (testCount++);
		String gitdirName = "test" + uniqueId + (bare ? "" : "/")
				+ Constants.DOT_GIT;
		File gitdir = new File(trash, gitdirName).getCanonicalFile();
		return gitdir;
	}

	protected File createTempFile() throws IOException {
		return new File(trash, "tmp-" + UUID.randomUUID()).getCanonicalFile();
	}

