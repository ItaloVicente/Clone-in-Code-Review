		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(sideCommit.getId()).call();
			assertNull(newHead);
			MergeResult result = revert.getFailingResult();
			assertEquals(MergeStatus.CONFLICTING
			assertTrue(new File(db.getDirectory()
			assertEquals("Revert \"" + sideCommit.getShortMessage()
					+ "\"\n\nThis reverts commit " + sideCommit.getId().getName()
					+ ".\n\nConflicts:\n\ta\n"
					db.readMergeCommitMsg());
			assertTrue(new File(db.getDirectory()
					.exists());
			assertEquals(sideCommit.getId()
			assertEquals(RepositoryState.REVERTING

			writeTrashFile("a"
			git.add().addFilepattern("a").call();

			assertEquals(RepositoryState.REVERTING_RESOLVED
					db.getRepositoryState());

			git.commit().setOnly("a").setMessage("resolve").call();

			assertEquals(RepositoryState.SAFE
		}
