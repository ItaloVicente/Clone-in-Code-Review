
	@Test
	public void testCloneBare() throws Exception {
		File gitDir = db.getDirectory();
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath()
				+ "/target.git";
		StringBuilder cmd = new StringBuilder("git clone --bare ")
				.append(sourcePath + " " + targetPath);
		execute(cmd.toString());
		Git git = Git.open(new File(targetPath));
		List<Ref> branches = git.branchList().call();
		assertEquals("expected 1 branch"
		assertTrue("expected bare repository"
	}
