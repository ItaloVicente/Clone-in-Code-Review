	protected File createUniqueTestGitDir(boolean bare) throws IOException {
		String uniqueId = System.currentTimeMillis() + "_" + (testCount++);
		String gitdirName = "test" + uniqueId + (bare ? "" : "/")
				+ Constants.DOT_GIT;
		File gitdir = new File(trash
		return gitdir;
	}

