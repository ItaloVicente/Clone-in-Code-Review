	@After
	public void tearDown() {
		ExtendedLogReaderService log = appContext.get(ExtendedLogReaderService.class);
		log.removeLogListener(logListener);
	}

