	@Test
	public void testHardResetWithConflicts_DoOverWriteUntrackedFile()
			throws JGitInternalException
			AmbiguousObjectException
		setupRepository();
		git.rm().setCached(true).addFilepattern("a.txt").call();
		assertTrue(new File(db.getWorkTree()
		git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD)
				.call();
		assertTrue(new File(db.getWorkTree()
	}

	@Test
	public void testHardResetWithConflicts_DoDeleteFileFolderConflicts()
			throws JGitInternalException
			AmbiguousObjectException
		setupRepository();
		writeTrashFile("d/c.txt"
		git.add().addFilepattern("d/c.txt").call();
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("d"

		git.reset().setMode(ResetType.HARD).setRef(Constants.HEAD)
				.call();
		assertFalse(new File(db.getWorkTree()
	}

