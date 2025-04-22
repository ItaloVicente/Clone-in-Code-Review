	private ObjectId insertTag(final Tag tag) throws IOException
			UnsupportedEncodingException {
		ObjectInserter oi = db.newObjectInserter();
		try {
			ObjectId id = oi.insert(Constants.OBJ_TAG
			oi.flush();
			tag.setTagId(id);
			return id;
		} finally {
			oi.release();
		}
	}

	private RevTag parseTag(AnyObjectId id) throws MissingObjectException
			IncorrectObjectTypeException
		RevWalk rw = new RevWalk(db);
		try {
			return rw.parseTag(id);
		} finally {
			rw.release();
		}
	}

