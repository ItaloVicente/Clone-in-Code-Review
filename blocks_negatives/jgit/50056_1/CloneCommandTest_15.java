		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setURI(fileUri());
		try {
			git2 = command.call();
