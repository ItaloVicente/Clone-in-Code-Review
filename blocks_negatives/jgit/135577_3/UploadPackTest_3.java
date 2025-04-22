		RepeatedSubtreeAtSameLevelPreparator preparator =
				new RepeatedSubtreeAtSameLevelPreparator();
		remote.update("master", preparator.commit);

		uploadV2WithTreeDepthFilter(4, preparator.commit.toObjectId());

		assertFalse(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.baz.toObjectId()));
		assertEquals(8, stats.getTreesTraversed());
	}

	@Test
	public void testWantFilteredObject() throws Exception {
		RepeatedSubtreePreparator preparator = new RepeatedSubtreePreparator();
		remote.update("master", preparator.commit);

		uploadV2WithTreeDepthFilter(
				3,
				preparator.commit.toObjectId(),
				preparator.foo.toObjectId());
		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));

		client = newRepo("client");
		uploadV2WithTreeDepthFilter(
				2,
				preparator.commit.toObjectId(),
				preparator.subtree3.toObjectId());
		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.subtree3.toObjectId()));
	}

	@Test
	public void testV2FetchFilterWhenNotAllowed() throws Exception {
