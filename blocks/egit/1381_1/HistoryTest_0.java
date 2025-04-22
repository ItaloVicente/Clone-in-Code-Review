	@Override
	public void tearDown() throws Exception {
		if (inserter != null) {
			inserter.release();
		}
		super.tearDown();
	}

