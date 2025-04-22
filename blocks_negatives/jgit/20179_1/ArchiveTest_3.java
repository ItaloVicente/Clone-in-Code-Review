	@Test
	public void testPrefixDoesNotNormalizeDoubleSlash() throws Exception {
		commitFoo();
		byte[] result = CLIGitCommand.rawExecute(
		assertArrayEquals(expect, listZipEntries(result));
	}

	@Test
	public void testPrefixDoesNotNormalizeDoubleSlashInTar() throws Exception {
		commitFoo();
		assertArrayEquals(expect, listTarEntries(result));
