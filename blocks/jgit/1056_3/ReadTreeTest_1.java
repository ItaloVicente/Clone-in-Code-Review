	public void testCheckoutUncachedChanges() throws IOException {
		setupCase(mk("foo")
		writeTrashFile("foo"
		checkout();
		assertIndex(mk("foo"));
		assertWorkDir(mkmap("foo"
		assertTrue(new File(trash
	}

