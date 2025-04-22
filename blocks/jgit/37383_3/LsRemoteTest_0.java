
	@Test
	public void testLsRemoteHeads() throws Exception {
		final List<String> result = CLIGitCommand.execute(
				"git ls-remote --heads "
				+ db.getDirectory()
		assertArrayEquals(new String[] {
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/heads/master"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/heads/test"
				"" }
	}
