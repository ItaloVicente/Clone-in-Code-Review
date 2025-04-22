		for (MTrimElement trimElement : trimBar.getChildren()) {
			if (!(trimElement instanceof MToolBar)) {
				continue;
			}
			MToolBar toolBar = (MToolBar) trimElement;
			ToolBarManager manager = toolbarMngrRenderer.getManager(toolBar);
			if (manager != null) {
				toolbarMngrRenderer.reconcileManagerToModel(manager, toolBar);
				IContributionItem contributionItem = (IContributionItem) toolBar.getTransientData().get(
						CoolBarToTrimManager.OBJECT);
				String text = getToolbarLabel(toolBar);
				DisplayItem toolBarEntry = new DisplayItem(text, contributionItem);
				toolBarEntry.setImageDescriptor(toolbarImageDescriptor);
				toolBarEntry.setActionSet(idToActionSet.get(getActionSetID(toolBar)));
				root.addChild(toolBarEntry);
				toolBarEntry.setCheckState(getToolbarItemIsVisible(toolBarEntry));
				if (!hasVisibleItems(toolBar)) {
					continue;
