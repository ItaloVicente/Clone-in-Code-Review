			db = null;
		}
		if (db == null)
			return;
		try {
			if (isEnabledFor(db))
				execute(client, db);
		} finally {
			db.close();
