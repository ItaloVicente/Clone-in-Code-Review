		assertEquals("subject\n\nbody of commit"
	}

	@Test
	public void testNoFooters_BodyWithLF() throws IOException {
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
	public void testNoFooters_NoBodyNoParagraph() throws IOException {
		final RevCommit commit = parse("subject\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();

		assertNotNull(footers);
		assertEquals(0
		assertEquals("subject Signed-off-by: A. U. Thor <a@example.com>"
		assertEquals("subject\nSigned-off-by: A. U. Thor <a@example.com>"
				msgWithoutFooter);
	}

	@Test
	public void testOneFooter_NoBody() throws IOException {
		final RevCommit commit = parse("subject\n\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
		assertEquals("subject"
		assertEquals("subject"
	}

	@Test
	public void testOneFooter_ShortBodyNoParagraph() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
		assertEquals("subject"
		assertEquals("subject"
	}


	@Test
	public void testSkipFooter_ShortBody() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n\n"
				+ "not really a footer line but we'll skip it anyway\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>\n"
				+ "not really a footer line but we'll skip it anyway\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
		assertEquals("subject"
		assertEquals("subject\n\nbody of commit"
	}

	@Test
	public void testSkipFooter_ShortBody2() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n\n"
				+ "not really a footer line but we'll skip it anyway\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
		assertEquals("subject"
		assertEquals("subject\n\nbody of commit"
	}

	@Test
	public void testSkipFooter_ShortBody3() throws IOException {
		final RevCommit commit = parse("subject\n\nbody of commit\n\n"
				+ "Signed-off-by: A. U. Thor <a@example.com>\n"
				+ "not really a footer line but we'll skip it anyway\n");
		final List<FooterLine> footers = commit.getFooterLines();
		final String msgWithoutFooter = commit.getMessageWithoutFooter();
		final String title = commit.getShortMessage();
		FooterLine f;

		assertNotNull(footers);
		assertEquals(1

		f = footers.get(0);
		assertEquals("Signed-off-by"
		assertEquals("A. U. Thor <a@example.com>"
		assertEquals("a@example.com"
		assertEquals("subject"
		assertEquals("subject\n\nbody of commit"
