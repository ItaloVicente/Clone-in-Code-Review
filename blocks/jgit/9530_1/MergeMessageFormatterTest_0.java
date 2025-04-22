
	@Test
	public void testFormatWithConflictsNoFooter() {
		String originalMessage = "Header Line\n\nCommit body";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals("Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n"
				message);
	}

	@Test
	public void testFormatWithConflictsWithFooter() {
		String originalMessage = "Header Line\n\nCommit body\n\nChangeId: I123456789123456789123456789123456789\n";
		String message = formatter.formatWithConflicts(originalMessage
				Arrays.asList(new String[] { "path1" }));
		assertEquals(
				"Header Line\n\nCommit body\n\nConflicts:\n\tpath1\n\nChangeId: I123456789123456789123456789123456789\n"
				message);
	}

