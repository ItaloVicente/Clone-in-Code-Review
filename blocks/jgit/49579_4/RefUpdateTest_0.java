	@Test
	public void testLink_NoticesNoop() throws IOException {
		Result update = db.updateRef("refs/heads/symref")
				.link("refs/heads/target");
		assertEquals(Result.NEW

		Ref ref = db.getRefDatabase().exactRef("refs/heads/symref");
		assertTrue(ref.isSymbolic());
		assertEquals("refs/heads/target"

		update = db.updateRef("refs/heads/symref")
				.link("refs/heads/target");
		assertEquals(Result.NO_CHANGE
	}

	@Test
	public void testLink_OldAndNewObjectId() throws IOException {
		RefUpdate ru = db.updateRef("refs/heads/target1");
		ru.setNewObjectId(db.resolve("refs/heads/master"));
		ru.update();

		ru = db.updateRef("refs/heads/target2");
		ru.setNewObjectId(db.resolve("refs/heads/master^"));
		ru.update();

		ru = db.updateRef("refs/heads/changingsymref");
		ru.link("refs/heads/target1");

		ru = db.updateRef("refs/heads/changingsymref");
		Result update = ru.link("refs/heads/target2");
		assertEquals(Result.FORCED

		assertEquals(db.resolve("refs/heads/master")
		assertEquals(db.resolve("refs/heads/master^")
	}

	@Test
	public void testLink_NotConfusedByRefsHeadsRefsHeadsTarget() throws IOException {
		RefUpdate ru = db.updateRef("refs/heads/refs/heads/confusingtarget");
		ru.setNewObjectId(db.resolve("refs/heads/master"));
		ru.update();

		ru = db.updateRef("refs/heads/confusingtargetsymref");
		ru.link("refs/heads/refs/heads/confusingtarget");

		ru = db.updateRef("refs/heads/confusingtargetsymref");
		Result update = ru.link("refs/heads/confusingtarget");
		assertEquals(Result.FORCED

		Ref ref = db.getRefDatabase().exactRef("refs/heads/confusingtargetsymref");
		assertTrue(ref.isSymbolic());
		assertEquals("refs/heads/confusingtarget"

		assertNull(ru.getNewObjectId());
	}

	@Test
	public void testLink_NotConfusedByRefsHeadsRefsHeadsSrc() throws IOException {
		RefUpdate ru = db.updateRef("refs/heads/target");
		ru.setNewObjectId(db.resolve("refs/heads/master"));
		ru.update();

		ru = db.updateRef("refs/heads/refs/heads/confusingsrc");
		ru.link("refs/heads/target");

		ru = db.updateRef("refs/heads/confusingsrc");
		Result update = ru.link("refs/heads/target");
		assertEquals(Result.NEW

		Ref ref = db.getRefDatabase().exactRef("refs/heads/confusingsrc");
		assertTrue(ref.isSymbolic());
		assertEquals("refs/heads/target"

		assertNull(ru.getOldObjectId());
	}

