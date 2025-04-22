	public void testMatchesBugId() {
		final RevCommit commit = parse("this is a commit subject for test\n"
				+ "Simple-Bug-Id: 42\n");
		final List<FooterLine> footers = commit.getFooterLines();

		assertNotNull(footers);
		assertEquals(1

		final FooterLine line = footers.get(0);
		assertNotNull(line);
		assertEquals("Simple-Bug-Id"
		assertEquals("42"

		final FooterKey bugid = new FooterKey("Simple-Bug-Id");
		assertTrue("matches Simple-Bug-Id"
		assertFalse("not Signed-off-by"
		assertFalse("not CC"
	}

