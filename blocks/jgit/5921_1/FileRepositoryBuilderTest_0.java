
	@Test
	public void absoluteGitDirRef() throws Exception {
		FileRepository repo1 = createWorkRepository();
		File dir = createTempDirectory("dir");
		File dotGit = new File(dir
		new FileWriter(dotGit).append(
				"gitdir: " + repo1.getDirectory().getAbsolutePath()).close();
		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		builder.setWorkTree(dir);
		builder.setMustExist(true);
		FileRepository repo2 = builder.build();

		assertEquals(repo1.getDirectory()
		assertEquals(dir
	}

	@Test
	public void relativeGitDirRef() throws Exception {
		FileRepository repo1 = createWorkRepository();
		File dir = new File(repo1.getWorkTree()
		assertTrue(dir.mkdir());
		File dotGit = new File(dir
		new FileWriter(dotGit).append("gitdir: ../" + Constants.DOT_GIT)
				.close();

		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		builder.setWorkTree(dir);
		builder.setMustExist(true);
		FileRepository repo2 = builder.build();

		assertEquals(repo1.getDirectory()
		assertEquals(dir
	}

	@Test
	public void scanWithGitDirRef() throws Exception {
		FileRepository repo1 = createWorkRepository();
		File dir = createTempDirectory("dir");
		File dotGit = new File(dir
		new FileWriter(dotGit).append(
				"gitdir: " + repo1.getDirectory().getAbsolutePath()).close();
		FileRepositoryBuilder builder = new FileRepositoryBuilder();

		builder.setWorkTree(dir);
		builder.findGitDir(dir);
		assertEquals(repo1.getDirectory()
		builder.setMustExist(true);
		FileRepository repo2 = builder.build();

		assertEquals(repo1.getDirectory()
		assertEquals(dir
	}
