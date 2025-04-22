		Git git = new Git(db);
		writeTrashFile("fileAinfsonly", "A");
		File fileBinindex = writeTrashFile("fileBinindex", "B");
		fsTick(fileBinindex);
		git.add().addFilepattern("fileBinindex").call();
		writeTrashFile("fileCinfsonly", "C");
		TreeWalk tw = new TreeWalk(db);
		DirCacheIterator indexIter = new DirCacheIterator(db.readDirCache());
		FileTreeIterator workTreeIter = new FileTreeIterator(db);
		tw.addTree(indexIter);
		tw.addTree(workTreeIter);
		workTreeIter.setDirCacheIterator(tw, 0);
		assertEntry("d46c305e85b630558ee19cc47e73d2e5c8c64cdc", "a,, tw);
-		assertEntry(58ee403f98538ec02409538b3f80adf610accdec", "a,b", tw);
		assertEntry("0000000000000000000000000000000000000000", "a", tw);
		assertEntry("b8d30ff397626f0f1d3538d66067edf865e201d6", "a0b", tw);
		assertEntry("8c7e5a667f1b771847fe88c01c3de34413a1b220",
				"fileAinfsonly", tw);
		assertEntry("7371f47a6f8bd23a8fa1a8b2a9479cdd76380e54", "fileBinindex",
				tw);
		assertEntry("96d80cd6c4e7158dbebd0849f4fb7ce513e5828c",
				"fileCinfsonly", tw);
		assertFalse(tw.next());
