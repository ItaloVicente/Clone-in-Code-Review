	@Test
	public void testCreateMissingObject() throws IOException {
		String name = "refs/heads/abc";
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		RefUpdate ru = db.updateRef(name);
		ru.setNewObjectId(bad);
		Result update = ru.update();
		assertEquals(Result.NEW

		Ref ref = db.exactRef(name);
		assertNotNull(ref);
		assertFalse(ref.isPeeled());
		assertEquals(bad

		try (RevWalk rw = new RevWalk(db)) {
			rw.parseAny(ref.getObjectId());
			fail("Expected MissingObjectException");
		} catch (MissingObjectException expected) {
			assertEquals(bad
		}

		RefDirectory refdir = (RefDirectory) db.getRefDatabase();
		try {
			refdir.pack(Arrays.asList(name));
		} catch (MissingObjectException expected) {
			assertEquals(bad
		}
	}

	@Test
	public void testUpdateMissingObject() throws IOException {
		String name = "refs/heads/abc";
		RefUpdate ru = updateRef(name);
		Result update = ru.update();
		assertEquals(Result.NEW
		ObjectId oldId = ru.getNewObjectId();

		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		ru = db.updateRef(name);
		ru.setNewObjectId(bad);
		update = ru.update();
		assertEquals(Result.REJECTED

		Ref ref = db.exactRef(name);
		assertNotNull(ref);
		assertEquals(oldId
	}

	@Test
	public void testForceUpdateMissingObject() throws IOException {
		String name = "refs/heads/abc";
		RefUpdate ru = updateRef(name);
		Result update = ru.update();
		assertEquals(Result.NEW

		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		ru = db.updateRef(name);
		ru.setNewObjectId(bad);
		update = ru.forceUpdate();
		assertEquals(Result.FORCED

		Ref ref = db.exactRef(name);
		assertNotNull(ref);
		assertFalse(ref.isPeeled());
		assertEquals(bad

		try (RevWalk rw = new RevWalk(db)) {
			rw.parseAny(ref.getObjectId());
			fail("Expected MissingObjectException");
		} catch (MissingObjectException expected) {
			assertEquals(bad
		}

		RefDirectory refdir = (RefDirectory) db.getRefDatabase();
		try {
			refdir.pack(Arrays.asList(name));
		} catch (MissingObjectException expected) {
			assertEquals(bad
		}
	}

