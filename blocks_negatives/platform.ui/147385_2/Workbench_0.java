		List<MWindow> windows = modelService.findElements(appCopy, null, MWindow.class);
		for (MWindow window : windows) {
			if (window instanceof MTrimmedWindow) {
				MTrimmedWindow trimmedWindow = (MTrimmedWindow) window;
				window.setMainMenu(null);
				for (MTrimBar trimBar : trimmedWindow.getTrimBars()) {
					cleanUpTrimBar(trimBar);
				}
			}
		}
		appCopy.getMenuContributions().clear();
		appCopy.getToolBarContributions().clear();
		appCopy.getTrimContributions().clear();
