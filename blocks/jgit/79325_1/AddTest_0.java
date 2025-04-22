
	@Test
	public void testAddAll() throws Exception {
		writeTrashFile("greeting"
		writeTrashFile("greeting2"
		assertArrayEquals(new String[] { "" }
				execute("git add --all"));

		DirCache cache = db.readDirCache();
		assertNotNull(cache.getEntry("greeting"));
		assertNotNull(cache.getEntry("greeting2"));
		assertEquals(2
	}
