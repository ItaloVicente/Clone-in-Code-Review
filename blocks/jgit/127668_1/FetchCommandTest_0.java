	@Test
	public void fetchAddUpdateDeleteBranches() throws Exception {
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

		remoteGit.commit().setMessage("commit").call();
		branchRef2 = remoteGit.branchCreate().setName(branch2).setForce(true).call();

		final String branch3 = "b3";
		final String remoteBranch3 = "test/" + branch3;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef3 = remoteGit.branchCreate().setName(branch3).setForce(true).call();

		git.fetch().setRemote("test").setRefSpecs(spec)
				.setRemoveDeletedRefs(true).call();
		assertEquals(null
		assertEquals(branchRef2.getObjectId()
		assertEquals(branchRef3.getObjectId()
	}

