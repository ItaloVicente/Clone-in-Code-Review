		writeTrashFile("dir-or-file/c.txt"
		git.add().addFilepattern("dir-or-file/c.txt").call();

		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("dir-or-file"

		git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD).call();
		assertFalse(new File(db.getWorkTree()
