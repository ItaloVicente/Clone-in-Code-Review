
	public void testPullConfigRenameLocalBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		localGit.branchCreate().setParameters("newFromMaster"
				null).call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromMaster"
		localGit.branchDelete().setBranchNames("newFromMaster").call();
		localGit.branchCreate().setParameters("newFromMaster"
				SetupUpstreamMode.TRACK).call();
		assertEquals("."
				"branch"
		localGit.branchRename().setOldName("newFromMaster").setNewName(
				"renamed").call();
		assertNull("."
				"branch"
		assertEquals("."
				"branch"
		localGit.branchDelete().setBranchNames("renamed").call();
		assertNull(localGit.getRepository().getConfig().getString("branch"
				"newFromRemote"
	}

	public void testRenameLocalBranch() throws Exception {
		git.branchCreate().setParameters("existing"
				.call();
		Ref branch = git.branchCreate().setParameters("fromMasterForRename"
				false
		assertEquals(Constants.R_HEADS + "fromMasterForRename"
				.getName());
		Ref renamed = git.branchRename().setOldName("fromMasterForRename")
				.setNewName("newName").call();
		assertEquals(Constants.R_HEADS + "newName"
		try {
			git.branchRename().setOldName(renamed.getName()).setNewName(
					"existing").call();
			fail("Should have failed");
		} catch (RefAlreadyExistsException e) {
		}
		try {
			git.branchRename().setNewName("In va lid").call();
			fail("Rename with invalid ref name should fail");
		} catch (InvalidRefNameException e) {
		}
	}

	public void testRenameRemoteTrackingBranch() throws Exception {
		Git localGit = setUpRepoWithRemote();
		Ref remoteBranch = localGit.branchList().setListMode(ListMode.REMOTE)
				.call().get(0);
		Ref renamed = localGit.branchRename()
				.setOldName(remoteBranch.getName()).setNewName("newRemote")
				.call();
		assertEquals(Constants.R_REMOTES + "newRemote"
	}
