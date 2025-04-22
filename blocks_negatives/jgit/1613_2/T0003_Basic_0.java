	public void test005_ReadSimpleConfig() {
		final Config c = db.getConfig();
		assertNotNull(c);
		assertEquals("0", c.getString("core", null, "repositoryformatversion"));
		assertEquals("0", c.getString("CoRe", null, "REPOSITORYFoRmAtVeRsIoN"));
		assertEquals("true", c.getString("core", null, "filemode"));
		assertEquals("true", c.getString("cOrE", null, "fIlEModE"));
		assertNull(c.getString("notavalue", null, "reallyNotAValue"));
	}

