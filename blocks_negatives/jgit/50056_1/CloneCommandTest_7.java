		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		command.setRemote(null);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals("+refs/heads/*:refs/heads/*", git2.getRepository()
				.getConfig().getStringList("remote", "origin", "fetch")[0]);
		assertEquals("origin", git2.getRepository().getConfig()
				.getString("branch", "test", "remote"));
