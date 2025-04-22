		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("subject\n\nbody of commit"
	}

	@Test
	public void testNoFooters_ShortBodyWithoutLF() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
