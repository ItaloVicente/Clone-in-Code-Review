			int dirCacheIndex = tw.addTree(new DirCacheIterator(repository
					.readDirCache()));
			int fileTreeIndex = tw.addTree(new FileTreeIterator(repository));
			int repositoryTreeIndex = tw.addTree(rw.parseTree(repository
					.resolve(Constants.HEAD)));

