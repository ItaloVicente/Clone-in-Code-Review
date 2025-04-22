	@Test
	public void testTrackingUpdate() throws Exception {
		Repository db2 = createBareRepository();

		String remote = "origin";
		String branch = "refs/heads/master";
		String trackingBranch = "refs/remotes/" + remote + "/master";

		Git git = new Git(db);

		RevCommit commit1 = git.commit().setMessage("Initial commit")
				.call();

		RefUpdate branchRefUpdate = db.updateRef(branch);
		branchRefUpdate.setNewObjectId(commit1.getId());
		branchRefUpdate.update();

		RefUpdate trackingBranchRefUpdate = db.updateRef(trackingBranch);
		trackingBranchRefUpdate.setNewObjectId(commit1.getId());
		trackingBranchRefUpdate.update();

		final StoredConfig config = db.getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		URIish uri = new URIish(db2.getDirectory().toURI().toURL());
		remoteConfig.addURI(uri);
		remoteConfig.update(config);
		config.save();


		RevCommit commit2 = git.commit().setMessage("Commit to push").call();

		RefSpec spec = new RefSpec(branch + ":" + branch);
		Iterable<PushResult> resultIterable = git.push().setRemote(remote)
				.setRefSpecs(spec).call();

		PushResult result = resultIterable.iterator().next();
		TrackingRefUpdate trackingRefUpdate = result
				.getTrackingRefUpdate(trackingBranch);

		assertNotNull(trackingRefUpdate);
		assertEquals(trackingBranch
		assertEquals(branch
		assertEquals(commit2.getId()
		assertEquals(commit2.getId()
		assertEquals(commit2.getId()
	}

