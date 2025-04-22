		final ObjectReader curs = db.newObjectReader();
		try {
			return new CanonicalTreeParser(null, db, treeId, curs);
		} finally {
			curs.release();
		}
