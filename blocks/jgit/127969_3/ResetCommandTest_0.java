	@Test
	public void testHardResetWithConflicts_DeleteFolder_UnstagedChanges() throws Exception {
		setupRepository();
		ObjectId prevHead = db.resolve(Constants.HEAD);

		writeTrashFile("dir-or-file/c.txt"
		git.add().addFilepattern("dir-or-file/c.txt").call();
		git.commit().setMessage("adding dir-or-file/c.txt").call();

		writeTrashFile("dir-or-file-2/d.txt"
		git.add().addFilepattern("dir-or-file-2/d.txt").call();
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("dir-or-file-2"

		git.reset().setMode(ResetType.HARD).setRef(prevHead.getName()).call();
		assertFalse(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
	}

