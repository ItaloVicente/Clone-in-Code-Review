		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		command.setRemote("upstream");
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals("+refs/heads/*:refs/heads/*",
				git2.getRepository()
					.getConfig()
					.getStringList("remote", "upstream",
							"fetch")[0]);
		assertEquals("upstream",
				git2.getRepository()
					.getConfig()
					.getString("branch", "test", "remote"));
		assertNull(git2.getRepository().resolve("upstream/test"));
