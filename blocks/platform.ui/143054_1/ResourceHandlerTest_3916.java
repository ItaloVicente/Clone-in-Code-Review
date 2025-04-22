		resource = handler.loadMostRecentModel();
		application = (MApplication) resource.getContents().get(0);

	}

	@Test
	public void testProcessedApplicationModelNotNull() {
