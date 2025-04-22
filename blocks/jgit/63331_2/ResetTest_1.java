		resetPath(false
	}

	@Test
	public void testResetPathDoubleDashNoRef() throws Exception {
		resetPath(true
	}

	@Ignore("Currently we cannote recognize if a name is a commit-ish or a path
			+ "so 'git reset a' will not work if 'a' is not a branch name but a file path")
	@Test
	public void testResetPathNoDoubleDashNoRef() throws Exception {
		resetPath(false
