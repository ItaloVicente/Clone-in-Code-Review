	@Test
	public void testCreateFromLightweightTag() throws Exception {
		RefUpdate rup = db.updateRef("refs/tags/V10");
		rup.setNewObjectId(initialCommit);
		rup.setExpectedOldObjectId(ObjectId.zeroId());
		rup.update();

		Ref branch = git.branchCreate().setName("FromLightweightTag")
				.setStartPoint("refs/tags/V10").call();
		assertEquals(initialCommit.getId()

	}

	@Test
	public void testCreateFromAnnotatetdTag() throws Exception {
		RevTag tag = git.tag().setName("V10").setObjectId(secondCommit).call();
		Ref branch = git.branchCreate().setName("FromAnnotatedTag")
				.setStartPoint("refs/tags/V10").call();
		assertFalse(tag.getId().equals(branch.getObjectId()));
		assertEquals(secondCommit.getId()
	}

