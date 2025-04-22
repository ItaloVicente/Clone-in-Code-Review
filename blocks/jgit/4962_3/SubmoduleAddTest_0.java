
	@Test
	public void addSubmoduleWithRelativeUri() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit commit = git.commit().setMessage("create file").call();

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		String uri = "./.git";
		command.setPath(path);
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);

		SubmoduleWalk generator = SubmoduleWalk.forIndex(db);
		assertTrue(generator.next());
		assertEquals(path
		assertEquals(commit
		assertEquals(uri
		assertEquals(path
		String fullUri = db.getDirectory().getAbsolutePath();
		if (File.separatorChar == '\\')
			fullUri = fullUri.replace('\\'
		assertEquals(fullUri
		assertNotNull(generator.getRepository());
		assertEquals(
				fullUri
				generator
						.getRepository()
						.getConfig()
						.getString(ConfigConstants.CONFIG_REMOTE_SECTION
								Constants.DEFAULT_REMOTE_NAME
								ConfigConstants.CONFIG_KEY_URL));
		assertEquals(commit

		Status status = Git.wrap(db).status().call();
		assertTrue(status.getAdded().contains(Constants.DOT_GIT_MODULES));
		assertTrue(status.getAdded().contains(path));
	}
