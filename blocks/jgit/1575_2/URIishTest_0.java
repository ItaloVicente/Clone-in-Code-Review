	public void testRelativePath() throws Exception {
		final String str = "../../foo/bar";
		URIish u = new URIish(str);
		assertNull(u.getScheme());
		assertFalse(u.isRemote());
		assertEquals(str
		assertEquals(str
		assertEquals(u
	}

