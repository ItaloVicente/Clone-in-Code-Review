		CloneCommand command = Git.cloneRepository();
		command.setBare(true);
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(new RefSpec("+refs/heads/*:refs/heads/*"),
				fetchRefSpec(git2.getRepository()));
