		Git git = new Git(db);
		RevCommit sideCommit = prepareRevert(git);

		RevertCommand revert = git.revert();
		RevCommit newHead = revert.include(sideCommit.getId()).call();
		assertNull(newHead);
		MergeResult result = revert.getFailingResult();
		assertEquals(MergeStatus.CONFLICTING, result.getMergeStatus());
		assertTrue(new File(db.getDirectory(), Constants.MERGE_MSG).exists());
		assertEquals("Revert \"" + sideCommit.getShortMessage()
				+ "\"\n\nThis reverts commit " + sideCommit.getId().getName()
				+ ".\n\nConflicts:\n\ta\n",
				db.readMergeCommitMsg());
		assertTrue(new File(db.getDirectory(), Constants.REVERT_HEAD)
				.exists());
		assertEquals(sideCommit.getId(), db.readRevertHead());
		assertEquals(RepositoryState.REVERTING, db.getRepositoryState());

		writeTrashFile("a", "a");
		git.add().addFilepattern("a").call();

		assertEquals(RepositoryState.REVERTING_RESOLVED,
				db.getRepositoryState());

		git.commit().setOnly("a").setMessage("resolve").call();

		assertEquals(RepositoryState.SAFE, db.getRepositoryState());
