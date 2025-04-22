		assertEquals(1, c);
		ReflogReader reader = db.getReflogReader(Constants.HEAD);
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("commit (amend):"));
		reader = db.getReflogReader(db.getBranch());
		assertTrue(reader.getLastEntry().getComment()
				.startsWith("commit (amend):"));
