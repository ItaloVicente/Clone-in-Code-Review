
	@Test
	public void testWrappingWithHashes() {
		String input = "short\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789 123456789 #23456789\n\n";
		String expected = "short\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789\n"
				+ "123456789 #23456789\n\n";
		assertEquals(expected,
				SpellcheckableMessageArea.wrapCommitMessage(input));
		input = "short\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789 123456789 1#3456789\n\n";
		expected = "short\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789 123456789\n"
				+ "1#3456789\n\n";
		assertEquals(expected,
				SpellcheckableMessageArea.wrapCommitMessage(input));
	}
