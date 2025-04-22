		mockSystemReader = new MockSystemReader() {
			@Override
			public long getCurrentTime() {
				return System.currentTimeMillis();
			}
		};
