	@Test
	public void idOffset() throws Exception {
		Git git = new Git(db);
		writeTrashFile("fileAinfsonly"
		File fileBinindex = writeTrashFile("fileBinindex"
		long fsTick = fsTick(fileBinindex);
		System.out.println("fstick took " + fsTick);
		git.add().addFilepattern("fileBinindex").call();
		writeTrashFile("fileCinfsonly"
		TreeWalk tw = new TreeWalk(db);
		DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
		FileTreeIterator workTreeIter = new FileTreeIterator(db);
		tw.addTree(indexIter);
		tw.addTree(workTreeIter);
		workTreeIter.setDirCacheIterator(tw
		assertEntry("d46c305e85b630558ee19cc47e73d2e5c8c64cdc"
		assertEntry("0000000000000000000000000000000000000000"
		assertEntry("b8d30ff397626f0f1d3538d66067edf865e201d6"
		assertEntry("8c7e5a667f1b771847fe88c01c3de34413a1b220"
				"fileAinfsonly"
		assertEntry("7371f47a6f8bd23a8fa1a8b2a9479cdd76380e54"
				tw);
		assertEntry("96d80cd6c4e7158dbebd0849f4fb7ce513e5828c"
				"fileCinfsonly"
		assertFalse(tw.next());
	}

	private void assertEntry(String sha1string
			throws MissingObjectException
			CorruptObjectException
		assertTrue(tw.next());
		assertEquals(path
		assertEquals(sha1string
	}

