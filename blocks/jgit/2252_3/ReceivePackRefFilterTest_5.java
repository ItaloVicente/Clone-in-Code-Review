	private ObjectInserter inserter;

	@After
	public void release() {
		if (inserter != null)
			inserter.release();
	}

