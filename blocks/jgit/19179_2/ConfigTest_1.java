	@Test
	public void readNamesInSubSectionRecursive() throws ConfigInvalidException {
				+ "b=1\n";
		final Config c = parse(configString
		Set<String> names = c.getNames("a"
		assertEquals("Subsection size"
		assertTrue("Subsection should contain \"x\""
		assertTrue("Subsection should contain \"y\""
		assertTrue("Subsection should contain \"z\""
		names = c.getNames("a"
		assertEquals("Subsection size"
		assertTrue("Subsection should contain \"a\""
		assertTrue("Subsection should contain \"b\""
	}

