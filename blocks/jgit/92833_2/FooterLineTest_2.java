		assertEquals("this is a commit"
	}

	@Test
	public void testNoFooters_LongTitle() throws IOException {
		final RevCommit commit = parse("this is a very\nlong title\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("this is a very long title"
		assertEquals("this is a very\nlong title"
