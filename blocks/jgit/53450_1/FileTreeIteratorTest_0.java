	@Test
	public void submoduleDirectoryAsGitLink() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		final String path = "sub";
		addSubmodule(path);

		TreeWalk walk = new TreeWalk(db);
		FileTreeIterator workTreeIter = new FileTreeIterator(db);
		walk.addTree(workTreeIter);
		walk.setFilter(PathFilter.create(path));

		assertTrue(walk.next());
		assertEquals(FileMode.GITLINK
	}

	@Test
	public void notSubmoduleDirectoryAsTree() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		File badDotGit = new File(trash
		assertTrue(badDotGit.getParentFile().mkdirs());
		assertTrue(badDotGit.createNewFile());

		TreeWalk walk = new TreeWalk(db);
		FileTreeIterator workTreeIter = new FileTreeIterator(db);
		walk.addTree(workTreeIter);
		walk.setFilter(PathFilter.create(badDotGit.getParentFile().getName()));

		assertTrue(walk.next());
		assertEquals(FileMode.TREE
	}

	private void addSubmodule(String path) throws Exception {
		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		repo.close();
		File file = new File(repo.getWorkTree()
		assertTrue(file.exists());
	}

