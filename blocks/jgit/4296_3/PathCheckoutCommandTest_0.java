	@Test
	public void testUpdateWorkingDirectoryFromIndex2() throws Exception {
		CheckoutCommand co = git.checkout();
		fsTick(git.getRepository().getIndexFile());

		File written1 = writeTrashFile(FILE1
		File written2 = writeTrashFile(FILE2
		fsTick(written2);

		writeTrashFile(FILE3
		git.add().addFilepattern(FILE3).call();
		fsTick(git.getRepository().getIndexFile());

		git.add().addFilepattern(FILE1).addFilepattern(FILE2).call();
		fsTick(git.getRepository().getIndexFile());

		writeTrashFile(FILE1
		writeTrashFile(FILE2
		fsTick(written2);

		co.addPath(FILE1).setStartPoint(secondCommit).call();

		assertEquals("2"
		assertEquals("a(modified again)"

		validateIndex(git);
	}

	public static void validateIndex(Git git) throws NoWorkTreeException
			IOException {
		DirCache dc = git.getRepository().lockDirCache();
		ObjectReader r = git.getRepository().getObjectDatabase().newReader();
		try {
			for (int i = 0; i < dc.getEntryCount(); ++i) {
				DirCacheEntry entry = dc.getEntry(i);
				if (entry.getLength() > 0)
					assertEquals(entry.getLength()
							entry.getObjectId()
			}
		} finally {
			dc.unlock();
			r.release();
		}
	}
