		dc = db.readDirCache();
		assertTrue(dc.getEntryCount() == 2);
		assertTrue(dc.getEntry("a").isSmudged());
		assertFalse(dc.getEntry("b").isSmudged());

		assertEquals("[[a, modTime(index/file): t0/t0, unsmudged], [b, modTime(index/file): t1/t1]]", indexState(modTimes));
		assertEquals("[[a, modTime(index/file): t0/t0, unsmudged], [b, modTime(index/file): t1/t1]]", indexState(modTimes));

