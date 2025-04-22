		ObjectInserter inserter = db.newObjectInserter();
		try {
			inserter.insert(commit);
			inserter.flush();
		} finally {
			inserter.release();
		}
