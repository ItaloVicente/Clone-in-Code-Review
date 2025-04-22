		waitInUI();
	}

	@Before
	public void prepare() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		if (!repo.getBranch().equals("master")) {
			BranchOperation bop = new BranchOperation(repo, "refs/heads/master");
			bop.execute(null);
		}
