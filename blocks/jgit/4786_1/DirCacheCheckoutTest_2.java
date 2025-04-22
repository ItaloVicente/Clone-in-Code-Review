	@Test
	public void testCheckoutOutChangesAutoCRLFfalse() throws IOException {
		setupCase(mk("foo")
		checkout();
		assertIndex(mkmap("foo/bar"
		assertWorkDir(mkmap("foo/bar"
	}

	@Test
	public void testCheckoutOutChangesAutoCRLFtrue() throws IOException {
		setupCase(mk("foo")
		db.getConfig().setString("core"
		checkout();
		assertIndex(mkmap("foo/bar"
		assertWorkDir(mkmap("foo/bar"
	}

