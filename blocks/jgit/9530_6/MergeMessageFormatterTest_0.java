
	@Test
	public void testFormatWithConflictsNoFooter() {
		String originalMessage = "Header Line\n\nCommit body\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals("Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n"
				message);
	}

	@Test
	public void testFormatWithConflictsNoFooterNoLineBreak() {
		String originalMessage = "Header Line\n\nCommit body";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals("Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n"
				message);
	}

	@Test
	public void testFormatWithConflictsWithFooters() {
		String originalMessage = "Header Line\n\nCommit body\n\nChangeId:"
				+ " I123456789123456789123456789123456789\nBug:1234567\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals(
				"Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n\n"
						+ "ChangeId: I123456789123456789123456789123456789\nBug:1234567\n"
				message);
	}

	@Test
	public void testFormatWithConflictsWithFooterlikeLineInBody() {
		String originalMessage = "Header Line\n\nCommit body\nBug:1234567\nMore Body\n\nChangeId:"
				+ " I123456789123456789123456789123456789\nBug:1234567\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals(
				"Header Line\n\nCommit body\nBug:1234567\nMore Body\n\nConflicts:\n\tpath1\n\n"
						+ "ChangeId: I123456789123456789123456789123456789\nBug:1234567\n"
				message);
	}
