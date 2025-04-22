	public void test008_readSectionNames() throws ConfigInvalidException {
		final Config c = parse("[a]\n [B]\n");
		Set<String> sections = c.getSections();
		assertTrue("Sections should contain \"a\""
		assertTrue("Sections should contain \"b\""
	}

	public void test009_readNamesInSection() throws ConfigInvalidException {
		String configString = "[core]\n" + "repositoryformatversion = 0\n"
				+ "filemode = false\n" + "logallrefupdates = true\n";
		final Config c = parse(configString);
		Set<String> names = c.getNames("core");
		assertEquals("Core section size"
		assertTrue("Core section should contain \"filemode\""
				.contains("filemode"));
	}

	public void test010_readNamesInSubSection() throws ConfigInvalidException {
				+ "b=1\n";
		final Config c = parse(configString);
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

