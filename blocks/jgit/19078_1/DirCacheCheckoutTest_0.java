	@Test
	public void testDirectoryFileConflicts_5b() throws Exception {
		doit(mk("DF/DF")
		assertRemoved("DF/DF");
		assertConflict("DF");
		assertEquals(0
