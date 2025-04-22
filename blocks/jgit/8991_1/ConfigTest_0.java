	static enum TestEnumToString {
		ONE_TWO;

		@Override
		public String toString() {
			return "--" + name().toLowerCase().replace('_'
		}
	}

	@Test
	public void testGetEnumToString() throws ConfigInvalidException {
		Config c = parse("[s \"b\"]\n\tc = --one-two\n");
		assertSame(TestEnumToString.ONE_TWO
				c.getEnum("s"
	}

	@Test
	public void testSetEnumToString() {
		final Config c = new Config();
		c.setEnum("s"
		assertEquals("[s \"b\"]\n\tc = --one-two\n"
	}

