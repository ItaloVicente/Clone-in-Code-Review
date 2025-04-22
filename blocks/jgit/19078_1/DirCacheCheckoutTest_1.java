		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testDirectoryFileConflicts_6b() throws Exception {
		setupCase(mk("DF/DF")
		writeTrashFile("DF"
		go();
		assertRemoved("DF/DF");
		assertConflict("DF");
		assertEquals(0
