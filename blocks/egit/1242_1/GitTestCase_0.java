		byte[] fileContents = IO.readFully(file);
		ObjectInserter inserter = repository.newObjectInserter();
		try {
			ObjectId objectId = inserter.insert(Constants.OBJ_BLOB, fileContents);
			inserter.flush();
			return objectId;
		} finally {
			inserter.release();
		}
