	@After
	public void tearDown() {
		LogReaderService logReaderService = appContext.get(LogReaderService.class);
		logReaderService.removeLogListener(listener);
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
	}
