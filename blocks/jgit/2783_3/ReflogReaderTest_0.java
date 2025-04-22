	@Test
	public void testReadLineWithMissingComment() throws Exception {
		setupReflog("logs/refs/heads/master"
		final ReflogReader reader = db.getReflogReader("master");
		Entry e = reader.getLastEntry();
		assertEquals(ObjectId
				.fromString("da85355dfc525c9f6f3927b876f379f46ccf826e")
				.getOldId());
		assertEquals(ObjectId
				.fromString("3e7549db262d1e836d9bf0af7e22355468f1717c")
				.getNewId());
		assertEquals("A O Thor Too"
		assertEquals("authortoo@wri.tr"
		assertEquals(120
		assertEquals("2009-05-22T23:36:40"
		assertEquals(""
				e.getComment());
	}

