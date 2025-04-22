	@PreDestroy
	public void tearDown() {
		renderer.clearModelToManager(mainMenu, menuManager);
		mainMenu = null;
		renderer = null;
	}

