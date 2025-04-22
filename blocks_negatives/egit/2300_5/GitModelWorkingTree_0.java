		tw.addTree(new DirCacheIterator(repo.readDirCache()));
		tw.addTree(new FileTreeIterator(repo));
		dirCacheIteratorNth = 0;

		NotIgnoredFilter notIgnoredFilter = new NotIgnoredFilter(1);
		tw.setFilter(AndTreeFilter.create(ANY_DIFF, notIgnoredFilter));
