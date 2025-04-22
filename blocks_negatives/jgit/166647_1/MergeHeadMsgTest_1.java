		assertEquals(db.readMergeCommitMsg(), null);
		assertFalse(new File(db.getDirectory(), "MERGE_MSG").exists());
		db.writeMergeCommitMsg(mergeMsg);
		assertEquals(db.readMergeCommitMsg(), mergeMsg);
		assertEquals(read(new File(db.getDirectory(), "MERGE_MSG")), mergeMsg);
		db.writeMergeCommitMsg(null);
		assertEquals(db.readMergeCommitMsg(), null);
		assertFalse(new File(db.getDirectory(), "MERGE_MSG").exists());
