		DfsReader reader = new DfsReader(db.getObjectDatabase());
		DfsPackFile packs[] = db.getObjectDatabase().getPacks();

		assertEquals(2, packs.length);
		DfsPackFile p1 = packs[0];
		assertEquals(PackSource.INSERT, p1.getPackDescription().getPackSource());
		assertTrue(p1.hasObject(reader, fooId));

		DfsPackFile p2 = packs[1];
		assertEquals(PackSource.INSERT, p2.getPackDescription().getPackSource());
		assertTrue(p2.hasObject(reader, fooId));
