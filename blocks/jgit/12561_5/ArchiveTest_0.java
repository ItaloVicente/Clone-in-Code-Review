	@Test
	public void testUnrecognizedFormat() throws Exception {
		final String[]expect = new String[] { "fatal: Unknown archive format 'nonsense'" };
		final String[] actual = execute("git archive --format=nonsense " + emptyTree);
		assertArrayEquals(expect
	}

