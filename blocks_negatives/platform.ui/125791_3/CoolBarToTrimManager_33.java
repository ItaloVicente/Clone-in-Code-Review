		if (renderer == null) {
			return;
		}
		for (ToolBarContributionItemExtension ext : toolbarExtensions) {
			ToolBarManager manager = renderer.getManager(ext.tb);
			if (manager != null) {
				manager.dispose();
				manager.removeAll();
			}
			renderer.clearModelToManager(ext.tb, null);
			ext.dispose();
		}
		toolbarExtensions.clear();
		List<MToolBar> toolbars = workbenchTrimElements.stream().filter(e -> e instanceof MToolBar)
				.map(e -> (MToolBar) e).collect(Collectors.toList());

		for (MToolBar mToolBar : toolbars) {
			ToolBarManager manager = renderer.getManager(mToolBar);
			if (manager != null) {
				manager.dispose();
				manager.removeAll();
			}
			renderer.clearModelToManager(mToolBar, null);
		}
