	@Override
	@After
	public void tearDown() throws Exception {
		db.close();
		bareRepo.close();
		super.tearDown();
	}

