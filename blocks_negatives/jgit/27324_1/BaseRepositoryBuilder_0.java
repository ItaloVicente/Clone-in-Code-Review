	public R build() throws IOException {
		R repo = (R) new FileRepository(setup());
		if (isMustExist() && !repo.getObjectDatabase().exists())
			throw new RepositoryNotFoundException(getGitDir());
		return repo;
	}
