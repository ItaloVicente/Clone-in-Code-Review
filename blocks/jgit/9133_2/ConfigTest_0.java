	@Test
	public void testGetInvalidEnum() throws ConfigInvalidException {
		Config c = parse("[a]\n\tb = invalid\n");
		try {
			c.getEnum("a"
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid value: a.b=invalid"
		}

		c = parse("[a \"b\"]\n\tc = invalid\n");
		try {
			c.getEnum("a"
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid value: a.b.c=invalid"
		}
	}

