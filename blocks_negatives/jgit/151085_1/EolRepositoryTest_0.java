		walk = beginWalk();
		if (dotGitattributes != null)
			collectEntryContentAndAttributes(F, ".gitattributes", null);
		collectEntryContentAndAttributes(F, fileCRLF.getName(), entryCRLF);
		collectEntryContentAndAttributes(F, fileLF.getName(), entryLF);
		collectEntryContentAndAttributes(F, fileMixed.getName(), entryMixed);
		endWalk();
	}

	private TreeWalk beginWalk() throws Exception {
		TreeWalk newWalk = new TreeWalk(db);
		newWalk.addTree(new FileTreeIterator(db));
		newWalk.addTree(new DirCacheIterator(db.readDirCache()));
		return newWalk;
	}

	private void endWalk() throws IOException {
		assertFalse("Not all files tested", walk.next());
