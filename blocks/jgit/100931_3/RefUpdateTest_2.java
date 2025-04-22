		ref.setExpectedOldObjectId(blobId);
		ref.setForceUpdate(true);
		delete(bareRepo

		head = bareRepo.exactRef(Constants.HEAD);
		assertNotNull(head);
		assertTrue(head.isSymbolic());
		assertEquals(master
		assertNull(head.getObjectId());
		assertNull(bareRepo.exactRef(master));
	}

	@Test
	public void testDeleteSymref() throws IOException {
		RefUpdate dst = updateRef("refs/heads/abc");
		assertEquals(Result.NEW
		ObjectId id = dst.getNewObjectId();

		RefUpdate u = db.updateRef("refs/symref");
		assertEquals(Result.NEW

		Ref ref = db.exactRef(u.getName());
		assertNotNull(ref);
		assertTrue(ref.isSymbolic());
		assertEquals(dst.getName()
		assertEquals(id

		u = db.updateRef(u.getName());
		u.setDetachingSymbolicRef();
		u.setForceUpdate(true);
		assertEquals(Result.FORCED

		assertNull(db.exactRef(u.getName()));
		ref = db.exactRef(dst.getName());
		assertNotNull(ref);
		assertFalse(ref.isSymbolic());
		assertEquals(id
