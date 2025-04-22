	public void testUpdateRefDetached() throws Exception {
		ObjectId pid = db.resolve("refs/heads/master");
		ObjectId ppid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("HEAD"
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(ppid);
		Result update = updateRef.update();
		assertEquals(Result.FORCED
		assertEquals(ppid
		Ref ref = db.getRef("HEAD");
		assertEquals("HEAD"
		assertEquals("HEAD"

		assertEquals(pid
		ReflogReader reflogReader = new  ReflogReader(db
		org.eclipse.jgit.lib.ReflogReader.Entry e = reflogReader.getReverseEntries().get(0);
		assertEquals(pid
		assertEquals(ppid
		assertEquals("GIT_COMMITTER_EMAIL"
		assertEquals("GIT_COMMITTER_NAME"
		assertEquals(1250379778000L
	}

	public void testUpdateRefDetachedUnbornHead() throws Exception {
		ObjectId ppid = db.resolve("refs/heads/master^");
		db.writeSymref("HEAD"
		RefUpdate updateRef = db.updateRef("HEAD"
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(ppid);
		Result update = updateRef.update();
		assertEquals(Result.NEW
		assertEquals(ppid
		Ref ref = db.getRef("HEAD");
		assertEquals("HEAD"
		assertEquals("HEAD"

		assertNull(db.resolve("refs/heads/unborn"));
		ReflogReader reflogReader = new  ReflogReader(db
		org.eclipse.jgit.lib.ReflogReader.Entry e = reflogReader.getReverseEntries().get(0);
		assertEquals(ObjectId.zeroId()
		assertEquals(ppid
		assertEquals("GIT_COMMITTER_EMAIL"
		assertEquals("GIT_COMMITTER_NAME"
		assertEquals(1250379778000L
	}

