		logged = false;

		LogReaderService logReaderService = appContext.get(LogReaderService.class);
		logReaderService.addLogListener(listener);
	}

	@After
	public void tearDown() throws Exception {
		LogReaderService logReaderService = appContext.get(LogReaderService.class);
		logReaderService.removeLogListener(listener);
	}

	private void checkLog() {
		try {
			Thread.sleep(100);
		} catch (Exception e) {
		}
		assertFalse(logged);
