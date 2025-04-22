
	@Test
	public void customRefSearchPathForWorktreeSupport() throws Exception {
		Repository repo = createWorkRepository();
		Git git = new Git(repo);
		RevCommit commit1 = git.commit().setMessage("initial commit").call();
		RevCommit commit2 = git.commit().setMessage("second commit").call();

		String worktreePath = "worktrees/foo/";
		File worktreeHome = new File(repo.getDirectory()
		worktreeHome.mkdirs();
		new FileWriter(new File(worktreeHome

		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(repo.getWorkTree());
		builder.setup();
		assertEquals(commit2.getId()

		List<String> refSearchPaths = new ArrayList<>();
		refSearchPaths.addAll(Arrays.asList(RefDirectory.getDefaultSearchPath()));
		refSearchPaths.add(worktreePath);

		builder = new FileRepositoryBuilder();
		builder.setWorkTree(repo.getWorkTree());
		builder.setRefSearchPaths(refSearchPaths);
		builder.setup();
		assertEquals(commit2.getId()

		refSearchPaths = new ArrayList<>();
		refSearchPaths.add(worktreePath);
		refSearchPaths.addAll(Arrays.asList(RefDirectory.getDefaultSearchPath()));

		builder = new FileRepositoryBuilder();
		builder.setWorkTree(repo.getWorkTree());
		builder.setRefSearchPaths(refSearchPaths);
		builder.setup();
		assertEquals(commit1.getId()
	}
