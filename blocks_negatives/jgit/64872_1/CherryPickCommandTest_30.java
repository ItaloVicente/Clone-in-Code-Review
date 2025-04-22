		assertEquals(CherryPickStatus.CONFLICTING, result.getStatus());
		assertTrue(new File(db.getDirectory(), Constants.MERGE_MSG).exists());
		assertEquals("side\n\nConflicts:\n\ta\n", db.readMergeCommitMsg());
		assertTrue(new File(db.getDirectory(), Constants.CHERRY_PICK_HEAD)
				.exists());
		assertEquals(sideCommit.getId(), db.readCherryPickHead());
		assertEquals(RepositoryState.CHERRY_PICKING, db.getRepositoryState());
