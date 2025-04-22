		String text = "[foo " + escapedSubsection + "]\nbar = value";
		Config c = parse(text);
		Set<String> subsections = c.getSubsections("foo");
		assertEquals("only one section"
		return subsections.iterator().next();
	}

	private static void assertIllegalArgumentException(Runnable r) {
		try {
			r.run();
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	private static void assertInvalidSubsection(String expectedMessage
			String escapedSubsection) {
		try {
			parseEscapedSubsection(escapedSubsection);
			fail("expected ConfigInvalidException");
		} catch (ConfigInvalidException e) {
			assertEquals(expectedMessage
		}
