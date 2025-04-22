	@Nullable
	private RevCommit nextForIterator() {
		try {
			return next();
		} catch (IOException e) {
			throw new RevWalkException(e);
		}
	}

