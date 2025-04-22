
		createBranch(localGit
		assertEquals("origin"
				"branch"
		localGit.branchDelete().setBranchNames("refs/heads/newFromRemote")
				.call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"

