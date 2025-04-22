	@Test
	public void testUnrecognizedFormat() throws Exception {
		final List<String> expect = new String[] { "fatal: Unknown archive format 'nonsense'" };
		final List<String> actual = CLIGitCommand.execute(
				"git archive --format=nonsense " + emptyTree
		assertArrayEquals(expect
	}

