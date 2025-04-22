
	@Test
	public void dontWrapShortMessage() {
		String input = "short";
		assertEquals(input, SpellcheckableMessageArea.wrapCommitMessage(input));
	}

	@Test
	public void dontWrapLongCommitMessageFooter() {
		String input = "short\n\nfoo\n\n"
				+ "Change-Id: I0000000000000000000000000000000000000000\n"
				+ "Signed-off-by: Some-Arguablylong Name <jsomearguablylong.name@somecompany.com>";
		assertEquals(input, SpellcheckableMessageArea.wrapCommitMessage(input));
	}

	@Test
	public void wrapOnlyCommitMessageBody() {
		String input = "short\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789\n\n"
				+ "Change-Id: I0000000000000000000000000000000000000000\n"
				+ "Signed-off-by: Some-Arguablylong Name <somearguablylong.name@somecompany.com>";
		String expected = "short\n\n"
				+ "123456789 123456789 123456789 123456789 123456789 123456789 123456789\n"
				+ "123456789\n\n"
				+ "Change-Id: I0000000000000000000000000000000000000000\n"
				+ "Signed-off-by: Some-Arguablylong Name <somearguablylong.name@somecompany.com>";
		assertEquals(expected,
				SpellcheckableMessageArea.wrapCommitMessage(input));
	}
