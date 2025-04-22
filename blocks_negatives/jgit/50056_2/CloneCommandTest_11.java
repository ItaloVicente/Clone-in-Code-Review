		CloneCommand command = Git.cloneRepository();
		command.setBranch("test");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals("refs/heads/test", git2.getRepository().getFullBranch());
