
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
