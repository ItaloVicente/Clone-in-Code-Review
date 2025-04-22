
	@Test
	public void testStatusDefault() throws Exception {
		executeTest("git status"
	}

	@Test
	public void testStatusU() throws Exception {
		executeTest("git status -u"
	}

	@Test
	public void testStatusUno() throws Exception {
		executeTest("git status -uno"
	}

	@Test
	public void testStatusUall() throws Exception {
		executeTest("git status -uall"
	}

	@Test
	public void testStatusUntrackedFiles() throws Exception {
		executeTest("git status --untracked-files"
	}

	@Test
	public void testStatusUntrackedFilesNo() throws Exception {
		executeTest("git status --untracked-files=no"
	}

	@Test
	public void testStatusUntrackedFilesAll() throws Exception {
		executeTest("git status --untracked-files=all"
	}

	@Test
	public void testStatusPorcelain() throws Exception {
		executeTest("git status --porcelain"
	}

	@Test
	public void testStatusPorcelainU() throws Exception {
		executeTest("git status --porcelain -u"
	}

	@Test
	public void testStatusPorcelainUno() throws Exception {
		executeTest("git status --porcelain -uno"
	}

