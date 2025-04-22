	private void commitBazAndFooSlashBar() throws Exception {
		writeTrashFile("baz"
		writeTrashFile("foo/bar"
		git.add().addFilepattern("baz").call();
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("sample commit").call();
	}

	@Test
	public void testArchivePrefixOption() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.rawExecute(
				"git archive --prefix=x/ --format=zip master"
		String[] expect = { "x/baz"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarPrefixOption() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.rawExecute(
				"git archive --prefix=x/ --format=tar master"
		String[] expect = { "x/baz"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	private void commitFoo() throws Exception {
		writeTrashFile("foo"
		git.add().addFilepattern("foo").call();
		git.commit().setMessage("boring commit").call();
	}

	@Test
	public void testPrefixDoesNotNormalizeDoubleSlash() throws Exception {
		commitFoo();
		byte[] result = CLIGitCommand.rawExecute(
		assertArrayEquals(expect
	}

	@Test
	public void testPrefixDoesNotNormalizeDoubleSlashInTar() throws Exception {
		commitFoo();
		assertArrayEquals(expect
	}

	@Test
	public void testPrefixWithoutTrailingSlash() throws Exception {
		commitBazAndFooSlashBar();
		byte[] result = CLIGitCommand.rawExecute(
				"git archive --prefix=my- --format=zip master"
		String[] expect = { "my-baz"
		String[] actual = listZipEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

	@Test
	public void testTarPrefixWithoutTrailingSlash() throws Exception {
		commitBazAndFooSlashBar();
				"git archive --prefix=my- --format=tar master"
		String[] expect = { "my-baz"
		String[] actual = listTarEntries(result);

		Arrays.sort(expect);
		Arrays.sort(actual);
		assertArrayEquals(expect
	}

