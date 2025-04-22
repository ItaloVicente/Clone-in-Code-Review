	@Test
	public void testInitDirectoryInitialBranch() throws Exception {
		File workDirectory = tempFolder.getRoot();
		File gitDirectory = new File(workDirectory

		String[] result = execute(
				"git init -b main '" + workDirectory.getCanonicalPath() + "'");

		String[] expecteds = new String[] {
				"Initialized empty Git repository in "
						+ gitDirectory.getCanonicalPath()
				"" };
		assertArrayEquals(expecteds

		try (Repository repo = new FileRepository(gitDirectory)) {
			assertEquals("refs/heads/main"
		}
	}
