	public void commit() throws IOException {
		if (getCommitId() != null)
			throw new IllegalStateException(MessageFormat.format(JGitText.get().commitAlreadyExists, getCommitId()));
		ObjectInserter odi = objdb.newObjectInserter();
		try {
			ObjectId id = odi.insert(Constants.OBJ_COMMIT, odi.format(this));
			odi.flush();
			setCommitId(id);
		} finally {
			odi.release();
