		final WindowCursor curs = new WindowCursor();
		try {
			tw.addTree(new CanonicalTreeParser(pathPrefix, db, tree
					.toObjectId(), curs));
		} finally {
			curs.release();
		}
