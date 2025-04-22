		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setGitDir(gDir);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(directory, git2.getRepository().getWorkTree());
		assertEquals(gDir, git2.getRepository()
				.getDirectory());
		assertTrue(new File(directory, ".git").isFile());
		assertFalse(new File(gDir, ".git").exists());
