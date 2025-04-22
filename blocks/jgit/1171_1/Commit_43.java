		ObjectInserter odi = objdb.newObjectInserter();
		try {
			ObjectId id = odi.insert(Constants.OBJ_COMMIT
			odi.flush();
			setCommitId(id);
		} finally {
			odi.release();
		}
