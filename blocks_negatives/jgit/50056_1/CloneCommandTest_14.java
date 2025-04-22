		command = Git.cloneRepository();
		command.setBranch("refs/heads/master");
		command.setBranchesToClone(Collections
				.singletonList("refs/heads/master"));
		command.setDirectory(directory);
		command.setURI(fileUri());
		command.setBare(true);
		git2 = command.call();
		addRepoToClose(git2.getRepository());
		assertNotNull(git2);
		assertEquals(git2.getRepository().getFullBranch(), "refs/heads/master");
		assertEquals("refs/heads/master", allRefNames(git2.branchList()
				.setListMode(ListMode.ALL).call()));
