	@Override
	public void close() {
		try {
			inserter.close();
		} finally {
			db.close();
		}
	}

