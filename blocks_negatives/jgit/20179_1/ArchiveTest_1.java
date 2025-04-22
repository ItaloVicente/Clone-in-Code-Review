		byte[] result = CLIGitCommand.rawExecute(
				"git archive --prefix=x/ --format=zip master", db);
		String[] expect = { "x/baz", "x/foo/", "x/foo/bar" };
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect, actual);
