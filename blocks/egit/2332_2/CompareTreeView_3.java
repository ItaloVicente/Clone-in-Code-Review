			int rightTreeIndex;
			if (!useIndex)
				rightTreeIndex = tw.addTree(new CanonicalTreeParser(null,
						repository.newObjectReader(), rightCommit.getTree()));
			else
				rightTreeIndex = tw.addTree(new DirCacheIterator(repository
						.readDirCache()));
