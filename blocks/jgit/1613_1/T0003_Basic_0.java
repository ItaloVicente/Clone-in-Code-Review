	private boolean getFilemode() throws IOException {
		if (db.getFS().supportsExecute()) {
			File tmp = File.createTempFile("try"

			db.getFS().setExecute(tmp
			final boolean on = db.getFS().canExecute(tmp);

			db.getFS().setExecute(tmp
			final boolean off = db.getFS().canExecute(tmp);
			tmp.delete();

			return on && !off;
		} else {
			return false;
		}
	}

