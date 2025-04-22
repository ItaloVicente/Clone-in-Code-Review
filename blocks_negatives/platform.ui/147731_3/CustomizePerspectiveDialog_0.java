		MTrimBar topTrim = customizeActionBars.coolBarManager.getTopTrim();
		topTrim.setToBeRendered(true);

		engine.createGui(topTrim, customizeActionBars.windowModel.getWidget(),
				customizeActionBars.windowModel.getContext());

		customizeActionBars.menuManager.updateAll(true);
		customizeActionBars.coolBarManager.update(true);

