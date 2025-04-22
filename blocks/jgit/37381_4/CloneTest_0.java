
	@Test
	public void testCloneBare() throws Exception {
		createInitialCommit();

		File gitDir = db.getDirectory();
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath()
				+ "/target.git";
		StringBuilder cmd = new StringBuilder("git clone --bare ")
				.append(sourcePath + " " + targetPath);
		String[] result = execute(cmd.toString());
		assertArrayEquals(new String[] {
				"Cloning into '" + targetPath + "'..."
		Git git2 = Git.open(new File(targetPath));
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 1 branch"
		assertTrue("expected bare repository"
	}
