	public void testStatusPorcelainUall() throws Exception {
		executeTest("git status --porcelain -uall"
	}

	@Test
	public void testStatusPorcelainUntrackedFiles() throws Exception {
		executeTest("git status --porcelain --untracked-files"
	}

	@Test
	public void testStatusPorcelainUntrackedFilesNo() throws Exception {
		executeTest("git status --porcelain --untracked-files=no"
	}

	@Test
	public void testStatusPorcelainUntrackedFilesAll() throws Exception {
		executeTest("git status --porcelain --untracked-files=all"
	}

	private void executeTest(String command
			boolean untrackedFiles) throws Exception {
