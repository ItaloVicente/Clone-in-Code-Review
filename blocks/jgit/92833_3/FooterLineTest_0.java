		assertEquals(""
	}

	@Test
	public void testNoFooters_OneChar() throws IOException {
		final RevCommit commit = parse("a");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("a"
