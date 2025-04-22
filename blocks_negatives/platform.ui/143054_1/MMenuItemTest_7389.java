		appContext.set(IWorkbench.PRESENTATION_URI_ARG, PartRenderingEngine.engineURI);
		ems = appContext.get(EModelService.class);
	}

	@After
	public void tearDown() {
		if (wb != null) {
			wb.close();
		}
		appContext.dispose();
