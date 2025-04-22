
	@Test
	public void wrapAlternatingBlankNonBlank() {
		String input = " x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x";
		String expected = " x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x\n"
				+ "x x x x x x";
		assertEquals(expected,
				SpellcheckableMessageArea.wrapCommitMessage(input));
	}

	@Test
	public void testWrappingLongWords() {
		String input = "A commit message with long words\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789 A_single_rather_long_word_that_exceeds_the_margin\n"
				+ "More text with normal short words on a single line fitting the margin.\n"
				+ "Even more text with normal short words on a single line exceeding the margin.\n\n"
				+ "[1] https://foo.example.org/with/a/very/long/url/that/should/remain/on/this/line/\n"
				+ " * https://foo.example.org/with/a/very/long/url/that/should/also/remain/on/this/line/\n"
				+ " * ** https://foo.example.org/with/a/very/long/url/that/should/also/remain/on/this/line/\n"
				+ "                                                this_long_word_also_should_remain_here, but this should be a new line\n"
				+ "     *                                          but_this_long_word_should_be_wrapped, and this should follow\n\n"
				+ "Change-Id: I0000000000000000000000000000000000000000\n"
				+ "Signed-off-by: Some-Arguablylong Name <somearguablylong.name@somecompany.com>";
		String expected = "A commit message with long words\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789\n"
				+ "A_single_rather_long_word_that_exceeds_the_margin\n"
				+ "More text with normal short words on a single line fitting the margin.\n"
				+ "Even more text with normal short words on a single line exceeding the\n"
				+ "margin.\n\n"
				+ "[1] https://foo.example.org/with/a/very/long/url/that/should/remain/on/this/line/\n"
				+ " * https://foo.example.org/with/a/very/long/url/that/should/also/remain/on/this/line/\n"
				+ " * ** https://foo.example.org/with/a/very/long/url/that/should/also/remain/on/this/line/\n"
				+ "                                                this_long_word_also_should_remain_here,\n"
				+ "but this should be a new line\n"
				+ "     *\n" // Trailing blanks stripped!
				+ "but_this_long_word_should_be_wrapped, and this should follow\n\n"
				+ "Change-Id: I0000000000000000000000000000000000000000\n"
				+ "Signed-off-by: Some-Arguablylong Name <somearguablylong.name@somecompany.com>";
		assertEquals(expected,
				SpellcheckableMessageArea.wrapCommitMessage(input));
	}
