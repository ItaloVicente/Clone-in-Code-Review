	@Test
	public void testCloseForcibly() throws Exception {
		db.incrementOpen();
		assertEquals(2
		assertFalse(db.isClosed());

		db.closeForcibly();
		assertEquals(0
		assertTrue(db.isClosed());

		RevCommit commit1 = commitFile("a"

		new GC(db).repack();
		ObjectDirectory odb = db.getObjectDatabase();
		assertEquals(1
		assertClosedFileDescriptor(odb.getPacks().iterator().next());
		assertEquals(0

		ObjectReader reader = db.newObjectReader();
		try (RevWalk rw = new RevWalk(reader)) {
			rw.parseAny(commit1.getId());
		}
		assertOpenFileDescriptor(odb.getPacks().iterator().next());
		assertEquals(1

		db.closeForcibly();
		assertEquals(0
		assertClosedFileDescriptor(odb.getPacks().iterator().next());
	}

