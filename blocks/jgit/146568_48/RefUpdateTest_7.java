
	@Test
	public void testOldValue() throws Exception {
		ObjectId cur = db.resolve("master");
		ObjectId prev = db.resolve("master^");
		RefUpdate u1 = db.updateRef("refs/heads/master");
		RefUpdate u2 = db.updateRef("refs/heads/master");

		u1.setExpectedOldObjectId(cur);
		u1.setNewObjectId(prev);
		u1.setForceUpdate(true);

		u2.setExpectedOldObjectId(cur);
		u2.setNewObjectId(prev);
		u2.setForceUpdate(true);

		assertEquals(FORCED
		assertEquals(LOCK_FAILURE
	}

	@Test
	public void testRenameAtomic() throws IOException {
		ObjectId prevId = db.resolve("refs/heads/master^");

		RefRename rename = db.renameRef("refs/heads/master"

		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(prevId);
		updateRef.setForceUpdate(true);
		assertEquals(FORCED
		assertEquals(RefUpdate.Result.LOCK_FAILURE
	}

	@Test
	public void testRenameSymref() throws IOException {
		db.resolve("HEAD");
		RefRename r = db.renameRef("HEAD"
		assertEquals(IO_FAILURE
	}

