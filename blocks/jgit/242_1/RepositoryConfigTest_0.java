	public void testBooleanWithNoValue() throws ConfigInvalidException {
		Config c = parse("[my]\n\tempty\n");
		assertEquals(""
		assertEquals(1
		assertEquals(""
		assertTrue(c.getBoolean("my"
		assertEquals("[my]\n\tempty\n"
	}

	public void testEmptyString() throws ConfigInvalidException {
		Config c = parse("[my]\n\tempty =\n");
		assertNull(c.getString("my"

		String[] values = c.getStringList("my"
		assertNotNull(values);
		assertEquals(1
		assertNull(values[0]);

		assertTrue(c.getBoolean("my"
		assertFalse(c.getBoolean("my"

		assertEquals("[my]\n\tempty =\n"

		c = new Config();
		c.setStringList("my"
		assertEquals("[my]\n\tempty =\n"
	}

