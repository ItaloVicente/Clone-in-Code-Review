
	@Test
	public void fetchAddAndDeleteBranchesWithDuplicateRefspec()
			throws Exception {
		final String branchName = "branch";
		final String remoteBranchName = "test/" + branchName;
		remoteGit.commit().setMessage("commit").call();
		Ref branchRef = remoteGit.branchCreate().setName(branchName).call();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addFetchRefSpec(new RefSpec(spec1));
		remoteConfig.addFetchRefSpec(new RefSpec(spec2));
		remoteConfig.update(config);

		git.fetch().setRemote("test").setRefSpecs(spec1).call();
		assertEquals(branchRef.getObjectId()

		remoteGit.branchDelete().setBranchNames(branchName).call();
		git.fetch().setRemote("test").setRefSpecs(spec1)
				.setRemoveDeletedRefs(true).call();
		assertEquals(null
	}
