	@Test
	public void testSquashFastForward() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("third commit").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()

		MergeResult result = git.merge().include(db.getRef("branch1"))
				.setSquash(true).call();

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertEquals(MergeResult.MergeStatus.FAST_FORWARD
				result.getMergeStatus());
		assertEquals(first
		assertEquals(first

		Status stat = git.status().call();
		assertEquals(StatusCommandTest.set("file2"
	}

	@Test
	public void testSquashMerge() throws Exception {
		Git git = new Git(db);

		writeTrashFile("file1"
		git.add().addFilepattern("file1").call();
		RevCommit first = git.commit().setMessage("initial commit").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("second commit").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/branch1");

		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("third commit").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/master");
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()

		MergeResult result = git.merge().include(db.getRef("branch1"))
				.setSquash(true).call();

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertEquals(MergeResult.MergeStatus.MERGED
		assertEquals(second
		assertEquals(second

		Status stat = git.status().call();
		assertEquals(StatusCommandTest.set("file3")
	}

