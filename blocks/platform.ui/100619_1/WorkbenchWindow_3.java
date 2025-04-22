	@PreDestroy
	void preDestroy() {
		if (mainMenu != null) {
			renderer.clearModelToManager(mainMenu, menuManager);
			mainMenu = null;
		}
		renderer = null;
	}

