	private ObjectId getObjectIdFromRef(Ref r) throws JGitInternalException {
		try {
			ObjectId key = repo.getRefDatabase().peel(r).getPeeledObjectId();
			if (key == null) {
				key = r.getObjectId();
			}
			return key;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
