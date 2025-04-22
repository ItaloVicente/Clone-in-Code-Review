		transport = null;
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (transport != null) {
			transport.close();
			transport = null;
		}
		super.tearDown();
