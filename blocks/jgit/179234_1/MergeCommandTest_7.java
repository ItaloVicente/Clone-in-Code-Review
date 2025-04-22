	@Test
	public void testDeletionOnSideOurs() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			git.rm().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.OURS).call();
			assertEquals(MergeStatus.MERGED

			assertTrue(new File(db.getWorkTree()
			assertEquals("1\na(main)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb\n3\n"
			assertTrue(git.status().call().isClean());
