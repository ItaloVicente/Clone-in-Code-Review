		DirCache treeB = db.readDirCache();
		DirCache treeO = db.readDirCache();
		DirCache treeT = db.readDirCache();

		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		bTreeBuilder.add(createEntry("libelf-po/a"
		bTreeBuilder.add(createEntry("libelf/c"

		oTreeBuilder.add(createEntry("Makefile"
		oTreeBuilder.add(createEntry("libelf-po/a"
		oTreeBuilder.add(createEntry("libelf/c"
