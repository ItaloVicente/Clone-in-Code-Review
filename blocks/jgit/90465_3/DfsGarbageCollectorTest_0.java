		ticks = 0;
		mockSystemReader = new MockSystemReader() {
			@Override
			public long getCurrentTime() {
				return super.getCurrentTime() + ticks++;
			}
		};
		SystemReader.setInstance(mockSystemReader);
	}

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
