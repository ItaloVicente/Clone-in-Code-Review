	@Parameter(1)
	public boolean describeUseAllTags;

	private Git git;

	@Parameters(name = "git tag -a {1}?-a: with git describe {0}?--tags:")
