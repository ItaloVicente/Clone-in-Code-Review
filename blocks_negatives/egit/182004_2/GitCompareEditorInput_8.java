			int baseTreeIndex;
			if (baseCommit == null) {
				checkIgnored = true;
				baseTreeIndex = tw.addTree(new FileTreeIterator(repository));
			} else
				baseTreeIndex = tw.addTree(new CanonicalTreeParser(null,
						repository.newObjectReader(), baseCommit.getTree()));
			int compareTreeIndex;
			if (!useIndex)
				compareTreeIndex = tw.addTree(new CanonicalTreeParser(null,
						repository.newObjectReader(), compareCommit.getTree()));
			else
				compareTreeIndex = tw.addTree(new DirCacheIterator(repository
						.readDirCache()));

