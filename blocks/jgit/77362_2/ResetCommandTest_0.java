	@Test
	public void testHardResetReflogDisabled() throws Exception {
		setupRepository();
		ObjectId prevHead = db.resolve(Constants.HEAD);
		ResetCommand reset = git.reset();
		assertSameAsHead(reset.setMode(ResetType.HARD)
				.setRef(initialCommit.getName()).disableRefLog(true).call());
		assertTrue("reflog should be disabled"
		ObjectId head = db.resolve(Constants.HEAD);
		assertEquals(initialCommit
		assertFalse(indexFile.exists());
		assertTrue(untrackedFile.exists());
		String fileInIndexPath = indexFile.getAbsolutePath();
		assertFalse(inHead(fileInIndexPath));
		assertFalse(inIndex(indexFile.getName()));
		assertReflogDisabled(head);
		assertEquals(prevHead
	}

