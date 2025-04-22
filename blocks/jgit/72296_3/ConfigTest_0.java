	@Test
	public void testReadMultipleValuesForName() throws ConfigInvalidException {
		Config c = parse("[foo]\nbar=false\nbar=true\n");
		assertTrue(c.getBoolean("foo"
	}

