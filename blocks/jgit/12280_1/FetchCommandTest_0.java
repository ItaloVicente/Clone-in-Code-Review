
	@Test
	public void fetchWithExplicitTagsShouldUpdateLocal() throws Exception {
		final String tagName = "foo";
		remoteGit.commit().setMessage("commit").call();
		Ref tagRef1 = remoteGit.tag().setName(tagName).call();

		git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.AUTO_FOLLOW).call();
		assertEquals(tagRef1.getObjectId()

		remoteGit.commit().setMessage("commit 2").call();
		Ref tagRef2 = remoteGit.tag().setName(tagName).setForceUpdate(true)
				.call();

		FetchResult result = git.fetch().setRemote("test").setRefSpecs(spec)
				.setTagOpt(TagOpt.FETCH_TAGS).call();
		TrackingRefUpdate update = result.getTrackingRefUpdate(Constants.R_TAGS
				+ tagName);
		assertEquals(RefUpdate.Result.FORCED
		assertEquals(tagRef2.getObjectId()
	}
