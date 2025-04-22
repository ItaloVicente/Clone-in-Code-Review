		return createRepository(new Options().setBare(bare).setAutoClose(autoClose));
	}

	protected FileRepository createRepository(Options options)
			throws IOException {
		File gitdir = createUniqueTestGitDir(options.bare());
		FileRepository db = new FileRepository(new FileRepositoryBuilder()
				.setGitDir(gitdir).setCacheRefs(options.useRefCache()).setup());
