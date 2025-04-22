		config = new PackConfig(db);
	}

	public void tearDown() throws Exception {
		if (writer != null)
			writer.release();
		super.tearDown();
