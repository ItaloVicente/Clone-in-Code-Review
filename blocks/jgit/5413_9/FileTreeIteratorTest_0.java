	@Test
	public void testIsModifiedFileSmudged() throws Exception {
		File f = writeTrashFile("file"
		Git git = new Git(db);
		fsTick(f);
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		writeTrashFile("file"
		DirCacheEntry dce = db.readDirCache().getEntry("file");
		FileTreeIterator fti = new FileTreeIterator(trash
				.getConfig().get(WorkingTreeOptions.KEY));
		while (!fti.getEntryPathString().equals("file"))
			fti.next(1);
		assertEquals(MetadataDiff.SMUDGED
		assertTrue(fti.isModified(dce
	}

