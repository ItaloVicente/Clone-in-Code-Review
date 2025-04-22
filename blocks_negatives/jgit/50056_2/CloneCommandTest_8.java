		CloneCommand command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setDirectory(directory);
		command.setURI(fileUri());
		Git git2 = command.call();
		addRepoToClose(git2.getRepository());

		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch(), "refs/heads/master");
		assertEquals(
				"refs/heads/master, refs/remotes/origin/master, refs/remotes/origin/test",
				allRefNames(git2.branchList().setListMode(ListMode.ALL).call()));
