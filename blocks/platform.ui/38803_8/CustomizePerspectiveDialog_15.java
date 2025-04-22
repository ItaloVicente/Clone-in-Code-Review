		if (OpaqueElementUtil.isOpaqueToolItem(element)) {
			if (contributionItem instanceof ActionContributionItem) {
				final IAction action = ((ActionContributionItem) contributionItem).getAction();
				DisplayItem toolbarEntry = new DisplayItem(action.getText(), contributionItem);
				toolbarEntry.setImageDescriptor(action.getImageDescriptor());
				toolbarEntry.setActionSet(idToActionSet.get(getActionSetID(contributionItem)));
				if (toolbarEntry.getChildren().isEmpty()) {
					toolbarEntry.setCheckState(getToolbarItemIsVisible(toolbarEntry));
