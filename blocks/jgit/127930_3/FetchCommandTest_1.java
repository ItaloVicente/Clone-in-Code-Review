
	@Test
	public void fetchAddRefsWithDuplicateRefspec() throws Exception {
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
	}

	@Test
	public void fetchPruneRefsWithDuplicateRefspec()
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
		assertNull(db.resolve(remoteBranchName));
	}

	@Test
	public void fetchUpdateRefsWithDuplicateRefspec() throws Exception {
		final String tagName = "foo";
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef1 = remoteGit.tag().setName(tagName).call();
		List<RefSpec> refSpecs = new ArrayList<>();
		git.fetch().setRemote("test").setRefSpecs(refSpecs)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(tagRef1.getObjectId()

		remoteGit.commit().setMessage("commit 2").call();
		Ref tagRef2 = remoteGit.tag().setName(tagName).setForceUpdate(true)
				.call();
		FetchResult result = git.fetch().setRemote("test").setRefSpecs(refSpecs)
				.setTagOpt(TagOpt.FETCH_TAGS).call();
		assertEquals(2
		TrackingRefUpdate update = result
				.getTrackingRefUpdate(Constants.R_TAGS + tagName);
		assertEquals(RefUpdate.Result.FORCED
		assertEquals(tagRef2.getObjectId()
	}
