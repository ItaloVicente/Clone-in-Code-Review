		recorder.assertNoEvent();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (handle != null) {
			handle.remove();
		}
		super.tearDown();
