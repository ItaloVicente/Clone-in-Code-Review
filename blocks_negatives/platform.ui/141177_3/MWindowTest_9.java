	@After
	public void tearDown() throws Exception {
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
		ContextInjectionFactory.setDefault(null);
	}
