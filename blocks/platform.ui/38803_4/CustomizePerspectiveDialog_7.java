			return ((PluginActionCoolBarContributionItem) item).getActionSetId();
		}
		if (item instanceof ContributionItem) {
			IContributionManager parent = ((ContributionItem) item).getParent();
			if (parent instanceof ActionSetMenuManager) {
				return ((ActionSetMenuManager) parent).getActionSetId();
			}
			if (item instanceof ToolBarContributionItem2) {
				return item.getId();
			}
