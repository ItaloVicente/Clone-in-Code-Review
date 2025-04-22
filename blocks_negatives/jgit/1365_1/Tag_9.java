	public void tag() throws IOException {
		if (getTagId() != null)
			throw new IllegalStateException(MessageFormat.format(JGitText.get().illegalStateExists, getTagId()));
		final ObjectId id;
		final RefUpdate ru;

		if (tagger!=null || message!=null || type!=null) {
			ObjectInserter odi = objdb.newObjectInserter();
			try {
				id = odi.insert(Constants.OBJ_TAG, odi.format(this));
				odi.flush();
				setTagId(id);
			} finally {
				odi.release();
			}
		} else {
			id = objId;
		}

		ru = objdb.updateRef(Constants.R_TAGS + getTag());
		ru.setNewObjectId(id);
		ru.setRefLogMessage("tagged " + getTag(), false);
		if (ru.forceUpdate() == RefUpdate.Result.LOCK_FAILURE)
			throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToLockTag, getTag()));
