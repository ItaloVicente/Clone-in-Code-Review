		DirCacheBuilder bTreeBuilder = treeB.builder();
		DirCacheBuilder oTreeBuilder = treeO.builder();
		DirCacheBuilder tTreeBuilder = treeT.builder();

		bTreeBuilder.add(createEntry("d/o"
		bTreeBuilder.add(createEntry("d/t"

		oTreeBuilder.add(createEntry("d/o"
		oTreeBuilder.add(createEntry("d/t"

		tTreeBuilder.add(createEntry("d/o"
		tTreeBuilder.add(createEntry("d/t"

		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow
