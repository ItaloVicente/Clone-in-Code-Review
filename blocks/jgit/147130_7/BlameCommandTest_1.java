	@Test
	public void testUnresolvedMergeConflict() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit base = commitFile("file.txt"

			RevCommit master = commitFile("file.txt"
					"Change on master branch\n"

			git.checkout().setName("side").setCreateBranch(true)
					.setStartPoint(base).call();
			RevCommit side = commitFile("file.txt"
					"Conflicting change on side\n"

			checkoutBranch("refs/heads/master");
			MergeResult result = git.merge().include(side).call();

			assertTrue("Expected a conflict"
					result.getConflicts().containsKey("file.txt"));

			BlameCommand command = new BlameCommand(db);
			command.setFilePath("file.txt");
			BlameResult lines = command.call();

			assertEquals(5
			assertNull(lines.getSourceCommit(0));
			assertEquals(master
			assertNull(lines.getSourceCommit(2));
			assertEquals(side
			assertNull(lines.getSourceCommit(4));
		}
	}

