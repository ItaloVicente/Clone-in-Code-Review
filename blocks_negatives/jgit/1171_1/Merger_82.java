		final WindowCursor curs = new WindowCursor();
		try {
			return new CanonicalTreeParser(null, db, treeId, curs);
		} finally {
			curs.release();
		}
