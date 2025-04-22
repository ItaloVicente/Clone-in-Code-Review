		MToolBarElement itemModel = (MToolBarElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		IContributionItem ici = getContribution(itemModel);
		if (ici == null) {
			return;
		}

		ToolBarManager parent = null;
		if (ici instanceof MenuManager) {
			parent = (ToolBarManager) ((MenuManager) ici).getParent();
		} else if (ici instanceof ContributionItem) {
			parent = (ToolBarManager) ((ContributionItem) ici).getParent();
		}
