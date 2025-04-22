		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("subject\n\nbody of commit"
	}

	@Test
	public void testNoFooters_LongBody() throws IOException {
		final RevCommit commit = parse(
				"subject\n\nbody of commit\nsecond body of commit");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("subject\n\nbody of commit\nsecond body of commit"
				msgWithoutFooter);
	}

	@Test
	public void testNoFooters_LongBodySLF() throws IOException {
		final RevCommit commit = parse(
				"subject\n\nbody of commit\nsecond body of commit\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("subject\n\nbody of commit\nsecond body of commit"
				msgWithoutFooter);
	}

	@Test
	public void testNoFooters_LongBodyWithDLF() throws IOException {
		final RevCommit commit = parse(
				"subject\n\nbody of commit\nsecond body of commit\n\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		assertNotNull(footers);
		assertEquals(0
		assertEquals("subject\n\nbody of commit\nsecond body of commit"
				msgWithoutFooter);
	}

	@Test
	public void testNoFooters_NoFooterParagraph() throws IOException {
		final RevCommit commit = parse("subject\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();

