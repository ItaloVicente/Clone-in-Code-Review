		final ObjectReader curs = repo.newObjectReader();
		try {
			return createSubtreeIterator(repo, new MutableObjectId(), curs);
		} finally {
			curs.release();
		}
