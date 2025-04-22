	public static Git open(File gitDir) throws IOException {
		Repository repo = new RepositoryBuilder().setGitDir(gitDir)
				.setMustExist(true).build();
		return new Git(repo);
	}

	public static Git wrap(Repository repo) {
		return new Git(repo);
	}

