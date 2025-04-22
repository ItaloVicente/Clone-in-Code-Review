	@Test
	public void testParseNonGitDescribe() throws IOException {
		ObjectId id = id("49322bb17d3acc9146f98c97d078513228bbf3c0");
		RefUpdate ru = db.updateRef("refs/heads/foo-g032c");
		ru.setNewObjectId(id);
		assertSame(RefUpdate.Result.NEW

		assertEquals(id
		assertEquals(id

		ru = db.updateRef("refs/heads/foo-g032c-dev");
		ru.setNewObjectId(id);
		assertSame(RefUpdate.Result.NEW

		assertEquals(id
		assertEquals(id
	}

