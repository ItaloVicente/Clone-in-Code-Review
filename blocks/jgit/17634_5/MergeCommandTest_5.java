
	@Test
	public void testBinaryMergeAdditions() throws Exception {
		Git git = new Git(db);
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		createBranch(initialCommit

		writeBinaryTrashFile("a"
		writeBinaryTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		git.commit().setMessage("master").call();

		checkoutBranch("refs/heads/side");

		writeBinaryTrashFile("b"
		writeBinaryTrashFile("c"
		git.add().addFilepattern("b").addFilepattern("c").call();
		RevCommit branchCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		MergeResult result = git.merge().include(branchCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertBinaryContentEquals("a"
		assertBinaryContentEquals("b"
		assertBinaryContentEquals("c"

		assertEquals(1
		assertEquals("b"

		assertEquals(RepositoryState.MERGING
	}

	@Test
	public void testBinaryMergeDeletions() throws Exception {
		Git git = new Git(db);

		writeBinaryTrashFile("a"
		writeBinaryTrashFile("b"
		writeBinaryTrashFile("c"
		writeBinaryTrashFile("d"
		writeBinaryTrashFile("e"
		git.add().addFilepattern("a").addFilepattern("b").addFilepattern("c")
				.addFilepattern("d").addFilepattern("e").call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		createBranch(initialCommit

		git.rm().addFilepattern("a").addFilepattern("b").addFilepattern("c")
				.call();
		git.commit().setMessage("master").call();

		checkoutBranch("refs/heads/side");

		writeBinaryTrashFile("b"
		writeBinaryTrashFile("d"
		git.rm().addFilepattern("c").addFilepattern("e").call();
		git.add().addFilepattern("b").addFilepattern("d").call();
		RevCommit branchCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		MergeResult result = git.merge().include(branchCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertFalse(new File(db.getWorkTree()
		assertBinaryContentEquals("b"
		assertFalse(new File(db.getWorkTree()
		assertBinaryContentEquals("d"
		assertFalse(new File(db.getWorkTree()

		assertEquals(1
		assertEquals("b"

		assertEquals(RepositoryState.MERGING
	}

	@Test
	public void testBinaryMergeChanges() throws Exception {
		Git git = new Git(db);

		writeBinaryTrashFile("a"
		writeBinaryTrashFile("b"
		writeBinaryTrashFile("c"
		git.add().addFilepattern("a").addFilepattern("b").addFilepattern("c")
				.call();
		RevCommit initialCommit = git.commit().setMessage("initial commit")
				.call();

		createBranch(initialCommit

		writeBinaryTrashFile("a"
		writeBinaryTrashFile("b"
		git.add().addFilepattern("a").addFilepattern("b").call();
		git.commit().setMessage("master").call();

		checkoutBranch("refs/heads/side");

		writeBinaryTrashFile("b"
		writeBinaryTrashFile("c"
		git.add().addFilepattern("b").addFilepattern("c").call();
		RevCommit branchCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		MergeResult result = git.merge().include(branchCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.CONFLICTING

		assertBinaryContentEquals("a"
		assertBinaryContentEquals("b"
		assertBinaryContentEquals("c"

		assertEquals(1
		assertEquals("b"

		assertEquals(RepositoryState.MERGING
	}

	private void writeBinaryTrashFile(String name
			throws IOException {
		final File path = new File(db.getWorkTree()
		FileUtils.mkdirs(path.getParentFile()
		final DataOutputStream output = new DataOutputStream(
				new FileOutputStream(
				path));
		try {
			output.writeByte(0x0);
			output.writeInt(data);
		} finally {
			output.close();
		}
	}

	private void assertBinaryContentEquals(String name
		final byte[] contents = readFile(name);
		final byte[] expected = new byte[5];
		expected[0] = 0x0;
		expected[1] = (byte)((data >>> 24) & 0xFF);
		expected[2] = (byte)((data >>> 16) & 0xFF);
		expected[3] = (byte)((data >>> 8) & 0xFF);
		expected[4] = (byte)((data >>> 0) & 0xFF);
		assertArrayEquals(expected
	}

	private byte[] readFile(String name) throws IOException {
		final File path = new File(db.getWorkTree()
		return IO.readFully(path);
	}

