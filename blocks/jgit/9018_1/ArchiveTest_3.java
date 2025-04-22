	@Test
	public void testTarPreservesContent() throws Exception {
		final String payload = "“The quick brown fox jumps over the lazy dog!”";
		writeTrashFile("xyzzy"
		git.add().addFilepattern("xyzzy").call();
		git.commit().setMessage("add file with content").call();

				"git archive --format=tar HEAD"
		assertArrayEquals(new String[] { payload }
				tarEntryContent(result
	}

	private Process spawnAssumingCommandPresent(String... cmdline) {
