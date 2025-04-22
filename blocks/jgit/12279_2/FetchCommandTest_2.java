	@Test
	public void fetchShouldAutoFollowTagForFetchedObjects() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef = remoteGit.tag().setName("foo").call();
		remoteGit.commit().setMessage("commit2").call();
		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(tagRef.getObjectId()
	}

	@Test
	public void fetchShouldNotFetchTagsFromOtherBranches() throws Exception {
		remoteGit.commit().setMessage("commit").call();
		remoteGit.checkout().setName("other").setCreateBranch(true).call();
		remoteGit.commit().setMessage("commit2").call();
		remoteGit.tag().setName("foo").call();
		RefSpec spec = new RefSpec(
				"refs/heads/master:refs/remotes/origin/master");
		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertNull(db.resolve("foo"));
	}

	@Test
	public void fetchWithUpdatedTagShouldNotTryToUpdateLocal() throws Exception {
		final String tagName = "foo";
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef = remoteGit.tag().setName(tagName).call();
		ObjectId originalId = tagRef.getObjectId();

		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(originalId

		remoteGit.commit().setMessage("commit 2").call();
		remoteGit.tag().setName(tagName).setForceUpdate(true).call();

		FetchResult result = git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();

		Collection<TrackingRefUpdate> refUpdates = result
				.getTrackingRefUpdates();
		assertEquals(1
		TrackingRefUpdate update = refUpdates.iterator().next();
		assertEquals("refs/heads/master"

		assertEquals(originalId
	}
