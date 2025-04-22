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
						"1\na(side)\n3\n"
						read(new File(db.getWorkTree()
				assertEquals("merge -X " + contentStrategy.name()
						read(new File(db.getWorkTree()
				git.reset().setMode(ResetType.HARD).setRef(thirdCommit.name())
						.call();
			}
		}
	}

	@Test
	public void testDeletionOnMasterTheirs() throws Exception {
