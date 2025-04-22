		CloneCommand command = Git.cloneRepository();
		command.setDirectory(directory);
		command.setGitDir(new File(directory, ".git"));
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertEquals(directory, git2.getRepository().getWorkTree());
		assertEquals(new File(directory, ".git"), git2.getRepository()
				.getDirectory());
