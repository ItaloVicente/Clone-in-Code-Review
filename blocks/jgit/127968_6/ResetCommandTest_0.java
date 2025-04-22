	@Test
	public void testHardResetWithConflicts_CreateFolder_UnstagedChanges() throws Exception {
		setupRepository();

		writeTrashFile("dir-or-file/c.txt"
		git.add().addFilepattern("dir-or-file/c.txt").call();
		git.commit().setMessage("adding dir-or-file/c.txt").call();

		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("dir-or-file"

		git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD).call();
		assertTrue(new File(db.getWorkTree()
	}

