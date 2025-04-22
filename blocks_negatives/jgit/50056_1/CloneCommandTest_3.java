		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setGitDir(gDir);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		try {
			assertNull(null, git2.getRepository().getWorkTree());
			fail("Expected NoWorkTreeException");
		} catch (NoWorkTreeException e) {
			assertEquals(gDir, git2.getRepository().getDirectory());
