		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(directory);
		clone.setURI(fileUri());
		Git git2 = clone.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);

		assertEquals(Constants.MASTER, git2.getRepository().getBranch());
