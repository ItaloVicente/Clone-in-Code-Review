
	private ObjectInserter inserter;

	@After
	public void release() {
		if (inserter != null)
			inserter.release();
	}

	private PackParser index(byte[] raw) throws IOException {
		if (inserter == null)
			inserter = repo.newObjectInserter();
		return inserter.newPackParser(new ByteArrayInputStream(raw));
	}
