	@Test
	public void testSubmoduleDeleteNotStagedWithUpdate() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		assertNotNull(git.commit().setMessage("create file").call());

		SubmoduleAddCommand command = new SubmoduleAddCommand(db);
		String path = "sub";
		command.setPath(path);
		String uri = db.getDirectory().toURI().toString();
		command.setURI(uri);
		Repository repo = command.call();
		assertNotNull(repo);
		assertNotNull(git.commit().setMessage("add submodule").call());

		assertTrue(git.status().call().isClean());

		FileUtils.delete(repo.getWorkTree(), FileUtils.RECURSIVE);
		FileUtils.mkdir(new File(db.getWorkTree(), path), false);

		assertNotNull(git.add().addFilepattern(".").setUpdate(true).call());

		Status status = git.status().call();
		assertFalse(status.isClean());
		assertTrue(status.getAdded().isEmpty());
		assertTrue(status.getChanged().isEmpty());
		assertTrue(status.getRemoved().isEmpty());
		assertTrue(status.getUntracked().isEmpty());
		assertTrue(status.getModified().isEmpty());
		assertEquals(1, status.getMissing().size());
		assertEquals(path, status.getMissing().iterator().next());
	}

