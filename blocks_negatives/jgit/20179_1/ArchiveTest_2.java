	public void testTarPrefixOption() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.rawExecute(
				"git archive --prefix=x/ --format=tar master", db);
		String[] expect = { "x/baz", "x/foo/", "x/foo/bar" };
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect, actual);
	}

	private void commitFoo() throws Exception {
