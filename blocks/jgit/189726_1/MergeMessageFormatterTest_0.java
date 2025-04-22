				List.of("path1")
		assertEquals("Header Line\n\nCommit body\n\n# Conflicts:\n#\tpath1\n"
				message);
	}

	@Test
	public void testFormatWithConflictsCustomCharacter() {
		String originalMessage = "Header Line\n\nCommit body";
		String message = formatter.formatWithConflicts(originalMessage
				List.of("path1")
		assertEquals("Header Line\n\nCommit body\n\n; Conflicts:\n;\tpath1\n"
