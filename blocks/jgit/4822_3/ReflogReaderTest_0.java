	@Test
	public void testSpecificEntryNumber() throws Exception {
		setupReflog("logs/refs/heads/master"

		ReflogReader reader = new ReflogReader(db
		ReflogEntry e = reader.getReverseEntry(0);
		assertEquals(
				ObjectId.fromString("c6734895958052a9dbc396cff4459dc1a25029ab")
				e.getOldId());
		assertEquals(
				ObjectId.fromString("54794942a18a237c57a80719afed44bb78172b10")
				e.getNewId());
		assertEquals("Same A U Thor"
		assertEquals("same.author@example.com"
		assertEquals(60
		assertEquals("2009-05-22T22:36:42"
		assertEquals(
				"rebase finished: refs/heads/rr/renamebranch5 onto c6e3b9fe2da0293f11eae202ec35fb343191a82d"
				e.getComment());

		e = reader.getReverseEntry(1);
		assertEquals(
				ObjectId.fromString("0000000000000000000000000000000000000000")
				e.getOldId());
		assertEquals(
				ObjectId.fromString("c6734895958052a9dbc396cff4459dc1a25029ab")
				e.getNewId());
		assertEquals("A U Thor"
		assertEquals("thor@committer.au"
		assertEquals(-60
		assertEquals("2009-05-22T20:36:41"
		assertEquals("branch: Created from rr/renamebranchv4"

		assertNull(reader.getReverseEntry(3));
	}

