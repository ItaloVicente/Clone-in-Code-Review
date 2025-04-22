		try (TreeWalk walk = new TreeWalk(db)) {
			walk.addTree(new FileTreeIterator(db));
			walk.addTree(new DirCacheIterator(db.readDirCache()));
			if (dotGitattributes != null) {
				collectEntryContentAndAttributes(walk
						null);
			}
			collectEntryContentAndAttributes(walk
					entryCRLF);
			collectEntryContentAndAttributes(walk
					entryLF);
			collectEntryContentAndAttributes(walk
					entryMixed);
			assertFalse("Not all files tested"
		}
