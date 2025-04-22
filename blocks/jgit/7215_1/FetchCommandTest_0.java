	@Test
	public void fetchOfTagUpdateShouldUpdateLocal() throws Exception {
		Git git1 = new Git(db);
		git1.commit().setMessage("commit").call();
		git1.tag().setName("tag1").call();

		Repository db2 = createWorkRepository();
		Git git2 = new Git(db2);
		git2.commit().setMessage("initial commit").call();
		Ref tagRef = git2.tag().setName("tag1").call();

		FetchResult result = git1.fetch()
				.setRemote(db2.getDirectory().getPath())
				.setTagOpt(TagOpt.FETCH_TAGS).setRefSpecs(spec).call();

		Collection<TrackingRefUpdate> refUpdates = result
				.getTrackingRefUpdates();
		assertEquals(1
		TrackingRefUpdate refUpdate = refUpdates.iterator().next();
		assertEquals("refs/tags/tag1"
		assertEquals(RefUpdate.Result.FORCED
		assertEquals(tagRef.getObjectId()
	}
