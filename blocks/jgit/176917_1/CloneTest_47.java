	@Test
	public void testCloneInitialBranch() throws Exception {
		createInitialCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		String cmd = "git clone --branch master " + sourceURI + " "
				+ shellQuote(target.getPath());
		String[] result = execute(cmd);
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."

		Git git2 = Git.open(target);
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 1 branch"

		Repository db2 = git2.getRepository();
		ObjectId head = db2.resolve("HEAD");
		assertNotNull(head);
		assertNotEquals(ObjectId.zeroId()
		ObjectId master = db2.resolve("master");
		assertEquals(head
	}

	@Test
	public void testCloneInitialBranchMissing() throws Exception {
		createInitialCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		String cmd = "git clone --branch foo " + sourceURI + " "
				+ shellQuote(target.getPath());
		Die e = assertThrows(Die.class
		assertEquals("Remote branch 'foo' not found in upstream origin"
				e.getMessage());
	}

