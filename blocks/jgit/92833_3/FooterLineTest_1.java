	@Test
	public void testNoFooters_OneCharNewLine() throws IOException {
		final RevCommit commit = parse("a\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("a"
	}


