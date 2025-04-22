	@Parameter(1)
	public boolean describeUseAllTags;

	private Git git;

	@Parameters(name = "git tag -a {0}?-a: with git describe {1}?--tags:")
