		setSearchContribution(appCopy, false);
		EModelService modelService = context.get(EModelService.class);
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

		List<MPart> parts = modelService.findElements(appCopy, null, MPart.class);
		for (MPart part : parts) {
			for (MMenu menu : part.getMenus()) {
				menu.getChildren().clear();
			}
			MToolBar tb = part.getToolbar();
			if (tb != null) {
				tb.getChildren().clear();
			}
		}
	}

	private static void cleanUpTrimBar(MTrimBar element) {
		element.getChildren().removeAll(element.getPendingCleanup());
		element.getPendingCleanup().clear();
	}
