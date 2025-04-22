	@Test
	public void fetchAddsBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchDoesntDeleteBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()

		remoteGit.branchDelete().setBranchNames(branch1).call();
		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchUpdatesBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()

		remoteGit.commit().setMessage("commit").call();
		branchRef2 = remoteGit.branchCreate().setName(branch2).setForce(true).call();
		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()
	}

	@Test
	public void fetchPrunesBranches() throws Exception {
		final String branch1 = "b1";
		final String branch2 = "b2";
		final String remoteBranch1 = "test/" + branch1;
		final String remoteBranch2 = "test/" + branch2;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef1 = remoteGit.branchCreate().setName(branch1).call();
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef2 = remoteGit.branchCreate().setName(branch2).call();

		git.fetch().setRemote("test").setRefSpecs(spec).call();
		assertEquals(branchRef1.getObjectId()
		assertEquals(branchRef2.getObjectId()

		remoteGit.branchDelete().setBranchNames(branch1).call();
		git.fetch().setRemote("test").setRefSpecs(spec)
				.setRemoveDeletedRefs(true).call();
		assertNull(db.resolve(remoteBranch1));
		assertEquals(branchRef2.getObjectId()
	}

