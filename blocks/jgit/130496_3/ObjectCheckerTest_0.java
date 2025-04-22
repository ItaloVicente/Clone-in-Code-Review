	@Test
	public void testValidTreeWithGitmodules() throws CorruptObjectException {
		ObjectId treeId = ObjectId
				.fromString("0123012301230123012301230123012301230123");
		StringBuilder b = new StringBuilder();
		ObjectId blobId = entry(b

		byte[] data = encodeASCII(b.toString());
		checker.checkTree(treeId
		assertEquals(1
		assertEquals(treeId
		assertEquals(blobId
	}

	@Test
	public void testNTFSGitmodules() throws CorruptObjectException {
		for (String gitmodules : new String[] {
			".GITMODULES"
			".gitmodules"
			".Gitmodules"
			".gitmoduleS"
			"gitmod~1"
			"GITMOD~1"
			"gitmod~4"
			"GI7EBA~1"
			"gi7eba~9"
			"GI7EB~10"
			"GI7E~123"
			"~1000000"
			"~9999999"
		}) {
			checker.setSafeForWindows(true);
			ObjectId treeId = ObjectId
					.fromString("0123012301230123012301230123012301230123");
			StringBuilder b = new StringBuilder();
			ObjectId blobId = entry(b

			byte[] data = encodeASCII(b.toString());
			checker.checkTree(treeId
			assertEquals(1
			assertEquals(treeId
			assertEquals(blobId
		}
	}

	@Test
	public void testNotGitmodules() throws CorruptObjectException {
		for (String notGitmodules : new String[] {
			".gitmodu"
			".gitmodules oh never mind"
		}) {
			checker.setSafeForWindows(true);
			ObjectId treeId = ObjectId
					.fromString("0123012301230123012301230123012301230123");
			StringBuilder b = new StringBuilder();
			entry(b

			byte[] data = encodeASCII(b.toString());
			checker.checkTree(treeId
			assertEquals(0
		}
	}


	@Test
	public void testValidTreeWithGitmodulesUppercase()
			throws CorruptObjectException {
		ObjectId treeId = ObjectId
				.fromString("0123012301230123012301230123012301230123");
		StringBuilder b = new StringBuilder();
		ObjectId blobId = entry(b

		byte[] data = encodeASCII(b.toString());
		checker.setSafeForWindows(true);
		checker.checkTree(treeId
		assertEquals(1
		assertEquals(treeId
		assertEquals(blobId
	}

	@Test
	public void testTreeWithInvalidGitmodules() throws CorruptObjectException {
		ObjectId treeId = ObjectId
				.fromString("0123012301230123012301230123012301230123");
		StringBuilder b = new StringBuilder();
		entry(b

		byte[] data = encodeASCII(b.toString());
		checker.checkTree(treeId
		checker.setSafeForWindows(true);
		assertEquals(0
	}

