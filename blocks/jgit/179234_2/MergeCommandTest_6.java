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
			RevCommit thirdCommit = git.commit().setMessage("main").call();

			for (ContentMergeStrategy contentStrategy : ContentMergeStrategy
					.values()) {
				MergeResult result = git.merge().include(secondCommit.getId())
						.setStrategy(MergeStrategy.RESOLVE)
						.setContentMergeStrategy(contentStrategy)
						.call();
				assertEquals("merge -X " + contentStrategy.name()
						MergeStatus.CONFLICTING

				assertTrue("merge -X " + contentStrategy.name()
						new File(db.getWorkTree()
				assertEquals("merge -X " + contentStrategy.name()
						"1\na(main)\n3\n"
						read(new File(db.getWorkTree()
				assertEquals("merge -X " + contentStrategy.name()
						read(new File(db.getWorkTree()

				assertNotNull("merge -X " + contentStrategy.name()
						result.getConflicts());
				assertEquals("merge -X " + contentStrategy.name()
						result.getConflicts().size());
				assertEquals("merge -X " + contentStrategy.name()
						result.getConflicts().get("a")[0].length);
				git.reset().setMode(ResetType.HARD).setRef(thirdCommit.name())
						.call();
			}
		}
	}

	@Test
	public void testDeletionOnSideTheirs() throws Exception {
