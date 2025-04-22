
		mockSystemReader = new MockSystemReader() {
			@Override
			public long getCurrentTime() {
				return tr.getClock().getTime();
			}
		};
		SystemReader.setInstance(mockSystemReader);
