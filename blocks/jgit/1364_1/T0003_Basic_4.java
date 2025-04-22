	private ObjectId insertCommit(final Commit commit) throws IOException
			UnsupportedEncodingException {
		ObjectInserter oi = db.newObjectInserter();
		try {
			ObjectId id = oi.insert(Constants.OBJ_COMMIT
			oi.flush();
			commit.setCommitId(id);
			return id;
		} finally {
			oi.release();
		}
	}

	private RevCommit parseCommit(AnyObjectId id)
			throws MissingObjectException
			IOException {
		RevWalk rw = new RevWalk(db);
		try {
			return rw.parseCommit(id);
		} finally {
			rw.release();
		}
	}

