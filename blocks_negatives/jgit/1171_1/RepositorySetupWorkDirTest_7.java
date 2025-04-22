	public void testNotBare_MakeBareUnbareBySetWorkdir() throws Exception {
		File gitDir = getFile("gitDir");
		Repository repo = new Repository(gitDir);
		repo.setWorkDir(getFile("workingDir"));
		assertFalse(repo.isBare());
		assertWorkdirPath(repo, "workingDir");
		assertGitdirPath(repo, "gitDir");
	}

