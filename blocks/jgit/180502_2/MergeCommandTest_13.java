			assertEquals("1\na(side)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb\n3\n"
			assertTrue(git.status().call().isClean());
		}
	}

	@Test
	public void testDeletionOnMasterOurs() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");
			git.rm().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.OURS).call();
			assertEquals(MergeStatus.MERGED

			assertFalse(new File(db.getWorkTree()
