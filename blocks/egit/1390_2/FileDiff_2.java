		ObjectReader reader = db.newObjectReader();
		try {
			outputEclipseDiff(d, db, reader, diffFmt);
		} finally {
			reader.release();
		}
