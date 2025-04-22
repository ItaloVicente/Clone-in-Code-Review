		final WindowCursor curs = new WindowCursor();
		try {
			return createSubtreeIterator(repo, new MutableObjectId(), curs);
		} finally {
			curs.release();
		}
