	private Git git;
	private Git remoteGit;

	@Before
	public void setupRemoteRepository() throws IOException
		git = new Git(db);
