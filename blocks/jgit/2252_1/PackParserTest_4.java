
	private ObjectInserter inserter;

	@After
	public void release() {
		if (inserter != null)
			inserter.release();
	}

	private PackParser index(InputStream in) throws IOException {
		if (inserter == null)
			inserter = db.newObjectInserter();
		return inserter.newPackParser(in);
	}
