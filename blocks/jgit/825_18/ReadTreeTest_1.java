	}

	public void testDirectoryFileConflicts_8() throws Exception {
		setupCase(mk("DF")
		recursiveDelete(new File(db.getWorkTree()
		writeTrashFile("DF"
		go();
		assertConflict("DF/DF");
