	@Test
	public void testAbortShouldAlsoAbortNonInteractiveRebaseWithRebaseApplyDir()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("initial commit").call();

		File applyDir = new File(db.getDirectory()
		File headName = new File(applyDir
		FileUtils.mkdir(applyDir);
		write(headName
		db.writeOrigHead(db.resolve(Constants.HEAD));

		git.rebase().setOperation(Operation.ABORT).call();

		assertFalse("Abort should clean up .git/rebase-apply"
				applyDir.exists());
		assertEquals(RepositoryState.SAFE
				.getRepositoryState());
	}

