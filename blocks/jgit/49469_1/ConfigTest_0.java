	@Test
	public void testExplicitlySetEmptyString() throws Exception {
		Config c = new Config();
		c.setString("a"
		c.setString("a"

		assertEquals("0"
		assertEquals(0

		assertEquals(""
		assertArrayEquals(new String[]{""}
		try {
			c.getInt("a"
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid integer value: a.y="
		}

		assertNull(c.getString("a"
		assertArrayEquals(new String[]{}
	}

	@Test
	public void testParsedEmptyString() throws Exception {
		Config c = parse("[a]\n"
				+ "x = 0\n"
				+ "y =\n");

		assertEquals("0"
		assertEquals(0

		assertEquals(""
		assertArrayEquals(new String[]{""}
		try {
			c.getInt("a"
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid integer value: a.y="
		}

		assertNull(c.getString("a"
		assertArrayEquals(new String[]{}
	}

	@Test
	public void testSetStringListWithEmptyValue() throws Exception {
		Config c = new Config();
		c.setStringList("a"
		assertArrayEquals(new String[]{""}
	}

