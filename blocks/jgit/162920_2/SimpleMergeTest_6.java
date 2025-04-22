		bTreeBuilder.finish();
		oTreeBuilder.finish();
		tTreeBuilder.finish();

		ObjectInserter ow = db.newObjectInserter();
		ObjectId b = commit(ow
		ObjectId o = commit(ow
		ObjectId t = commit(ow
