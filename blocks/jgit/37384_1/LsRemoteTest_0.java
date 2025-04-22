
	@Test
	public void testLsRemoteTags() throws Exception {
		final List<String> result = CLIGitCommand.execute(
				"git ls-remote --tags " + db.getDirectory()
		assertArrayEquals(new String[] {
				"efc02078d83a5226986ae917323acec7e1e8b7cb	refs/tags/tag1"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/tags/tag1^{}"
				"4e4b837e0fd4ba83c003678b03592dc1509a4115	refs/tags/tag2"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/tags/tag2^{}"
				"489384bf8ace47522fe32093d2ceb85b65a6cbb1	refs/tags/tag3"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/tags/tag3^{}"
				"" }
	}

	@Test
	public void testLsRemoteHeadsTags() throws Exception {
		final List<String> result = CLIGitCommand.execute(
				"git ls-remote --heads --tags " + db.getDirectory()
		assertArrayEquals(new String[] {
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/heads/master"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/heads/test"
				"efc02078d83a5226986ae917323acec7e1e8b7cb	refs/tags/tag1"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/tags/tag1^{}"
				"4e4b837e0fd4ba83c003678b03592dc1509a4115	refs/tags/tag2"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/tags/tag2^{}"
				"489384bf8ace47522fe32093d2ceb85b65a6cbb1	refs/tags/tag3"
				"d0b1ef2b3dea02bb2ca824445c04e6def012c32c	refs/tags/tag3^{}"
				"" }
	}

