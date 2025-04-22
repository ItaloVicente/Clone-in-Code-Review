		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setRemote("upstream");
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals("+refs/heads/*:refs/remotes/upstream/*",
				git2.getRepository()
					.getConfig()
					.getStringList("remote", "upstream",
							"fetch")[0]);
		assertEquals("upstream",
				git2.getRepository()
					.getConfig()
					.getString("branch", "test", "remote"));
		assertEquals(db.resolve("test"),
				git2.getRepository().resolve("upstream/test"));
