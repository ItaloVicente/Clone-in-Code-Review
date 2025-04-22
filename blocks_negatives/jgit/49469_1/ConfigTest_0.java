	@Test
	public void testEmptyString() throws ConfigInvalidException {
		Config c = parse("[my]\n\tempty =\n");
		assertNull(c.getString("my", null, "empty"));

		String[] values = c.getStringList("my", null, "empty");
		assertNotNull(values);
		assertEquals(1, values.length);
		assertNull(values[0]);

		assertTrue(c.getBoolean("my", "empty", true));
		assertFalse(c.getBoolean("my", "empty", false));

		assertEquals("[my]\n\tempty =\n", c.toText());

		c = new Config();
		c.setStringList("my", null, "empty", Arrays.asList(values));
		assertEquals("[my]\n\tempty =\n", c.toText());
	}

