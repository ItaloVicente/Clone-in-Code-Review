		byte[] result = CLIGitCommand.rawExecute(
				"git archive --prefix=my- --format=zip master", db);
		String[] expect = { "my-baz", "my-foo/", "my-foo/bar" };
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect, actual);
	}

	@Test
	public void testTarPrefixWithoutTrailingSlash() throws Exception {
		commitBazAndFooSlashBar();
				"git archive --prefix=my- --format=tar master", db);
		String[] expect = { "my-baz", "my-foo/", "my-foo/bar" };
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect, actual);
